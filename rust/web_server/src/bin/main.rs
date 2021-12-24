use std::io::{Read, Write};
use std::net::{TcpListener, TcpStream};
use std::time::Duration;
use std::{fs, thread};
use web_server::ThreadPool;

fn main() {
    let tcp_listener = TcpListener::bind("127.0.0.1:7878").unwrap();
    let pool = ThreadPool::new(4);

    for stream in tcp_listener.incoming().take(2) {
        let stream = stream.unwrap();
        pool.execute(|| handle_connection(stream));
    }
    println!("Hello, world!");
}

fn handle_connection(mut stream: TcpStream) {
    let mut buffer = [0u8; 1024];
    let _i = stream.read(&mut buffer).unwrap();

    let index_get_request_line = b"GET / HTTP/1.1\r\n";
    let sleep = b"GET /sleep HTTP/1.1\r\n";

    let (status_line, file_path_to_read) = if buffer.starts_with(index_get_request_line) {
        ("HTTP/1.1 200 OK", "hello.html")
    } else if buffer.starts_with(sleep) {
        thread::sleep(Duration::from_secs(10));
        ("HTTP/1.1 200 OK", "hello.html")
    } else {
        ("HTTP/1.1 404 NOT FOUND", "404.html")
    };

    write_response(&mut stream, status_line, file_path_to_read);
}

fn write_response(stream: &mut TcpStream, status_line: &str, file_path_to_read: &str) {
    let content = fs::read_to_string(file_path_to_read).unwrap();
    let response = format!(
        "{}\r\nContent-Length: {}\r\n\r\n{}",
        status_line,
        content.len(),
        content
    );
    stream.write(response.as_bytes()).unwrap();
    stream.flush().unwrap();
}
