use crate::Message::{NewJob, Terminate};
use std::sync::mpsc::{Receiver, Sender};
use std::sync::{mpsc, Arc, Mutex};
use std::thread;
use std::thread::JoinHandle;

pub struct ThreadPool {
    workers: Vec<Worker>,
    sender: Sender<Message>,
}

pub enum Message {
    NewJob(Job),
    Terminate,
}

type Job = Box<dyn FnOnce() + Send + 'static>;

impl ThreadPool {
    pub fn execute<F>(&self, f: F)
    where
        F: 'static + FnOnce() -> () + Send,
    {
        self.sender.send(Message::NewJob(Box::new(f))).unwrap();
    }

    ///Create a new ThreadPool
    ///
    /// The thread_num is the number of the thread in pool
    ///
    /// #Panic
    ///
    /// The `new` function will panic if the size is 0
    pub fn new(thread_num: usize) -> ThreadPool {
        assert!(thread_num > 0, "thread_num must L.T. 0");
        let mut workers: Vec<Worker> = Vec::with_capacity(thread_num);
        let (sender, receiver): (Sender<Message>, Receiver<Message>) = mpsc::channel();
        let receiver = Arc::new(Mutex::new(receiver));

        for idx in 0..thread_num {
            workers.push(Worker::new(idx, Arc::clone(&receiver)));
        }

        ThreadPool {
            workers,
            sender,
        }
    }
}

impl Drop for ThreadPool {
    fn drop(&mut self) {

        println!("Sending Terminate message to all workers");
        for _worker in &mut self.workers {
            self.sender.send(Terminate).unwrap();
        }

        println!("Shutting down all workers");
        for worker in &mut self.workers {
            println!("Shutting down worker {}", worker.id);
            if let Some(join_handler) = worker.thread.take() {
                join_handler.join().unwrap();
            }
        }
    }
}

pub struct Worker {
    id: usize,
    thread: Option<JoinHandle<()>>,
}

impl Worker {
    pub fn new(id: usize, receiver: Arc<Mutex<Receiver<Message>>>) -> Worker {
        Worker {
            id,
            thread: Some(thread::spawn(move || loop {
                match receiver.lock().unwrap().recv().unwrap() {
                    NewJob(f) => {
                        println!("worker {} get a job; executing", id);
                        f();
                    }
                    Terminate => {
                        println!("worker {} get a terminate message; terminating...", id);
                        break;
                    }
                }
            })),
        }
    }
}
