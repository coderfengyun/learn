fn main() {

    iterator();
}

pub fn iterator() {
    let v1 = vec![1, 2, 3];
    let iter = v1.iter();

    for iterm in iter {
        println!("{}", iterm)
    }
}