pub struct Post {
    state: Option<Box<dyn State>>,
    content: String,
}

pub trait State {

    fn request_review(self: Box<Self>) -> Box<dyn State> {
        self
    }

    fn approve(self: Box<Self>) -> Box<dyn State> {
        self
    }

    fn content<'a>(&self, post: &'a Post) -> &'a str {
        ""
    }
}

pub struct Draft {}

impl State for Draft {

    fn request_review(self: Box<Self>) -> Box<dyn State> {
        Box::new(PendingReview {})
    }
}

pub struct PendingReview {}

impl State for PendingReview {

    fn approve(self: Box<Self>) -> Box<dyn State> {
       Box::new(Published{})
    }
}

pub struct Published {}

impl State for Published {

    fn content<'a>(&self, post: &'a Post) -> &'a str {
        post.content.as_str()
    }
}

impl Post {

    pub fn new() -> Post {
        Post {
            state: Some(Box::new(Draft {})),
            content: String::new(),
        }
    }

    pub fn add_text(&mut self, text: &str) {
        self.content = String::from(text);
    }

    pub fn content(&self) -> &str {
        self.state.as_ref().unwrap().content(&self)
    }

    pub fn approve(&mut self) {
        if let Some(s) = self.state.take() {
            self.state = Some(s.approve());
        }
    }

    pub fn request_review(&mut self) {
        if let Some(s) = self.state.take() {
            self.state = Some(s.request_review());
        }
    }
}