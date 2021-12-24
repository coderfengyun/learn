use std::sync::mpsc::{Receiver, Sender};
use std::sync::{mpsc, Arc, Mutex};
use std::thread;
use std::thread::JoinHandle;

pub struct ThreadPool {
    workers: Vec<Worker>,
    sender: Sender<Job>,
}

type Job = Box<dyn FnOnce() + Send + 'static>;

impl ThreadPool {
    pub fn execute<F>(&self, f: F)
    where
        F: 'static + FnOnce() -> () + Send,
    {
        self.sender.send(Box::new(f));
    }
}

impl ThreadPool {
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
        let (sender, receiver): (Sender<Job>, Receiver<Job>) = mpsc::channel();
        let receiver = Arc::new(Mutex::new(receiver));

        for idx in 0..thread_num {
            workers.push(Worker::new(idx, Arc::clone(&receiver)));
        }

        ThreadPool { workers, sender }
    }
}

pub struct Worker {
    id: usize,
    thread: JoinHandle<()>,
}

impl Worker {
    pub fn new(id: usize, receiver: Arc<Mutex<Receiver<Job>>>) -> Worker {
        Worker {
            id,
            thread: thread::spawn(move || {
                loop {
                    let job = receiver.lock().unwrap().recv().unwrap();
                    println!("worker {} get a job; executing", id);
                    job();
                }
            }),
        }
    }
}