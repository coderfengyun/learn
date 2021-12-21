fn main() {
    let favourite_color: Option<&str> = None;
    let is_tuesday = false;
    let age: Result<u8, _> = ("34").parse();

    if let Some(color) = favourite_color {
        println!("Using my favourite color, {}, as Background", color);
    } else if is_tuesday {
        println!("Tuesday is green day");
    } else if let Ok(age) = age {
        if age > 30 {
            println!("Using purple as the background color");
        } else {
            println!("Using orange as the background color");
        }
    } else {
        println!("Using blue as the background color");
    }

    loop_read_from_stack();

    for_read_from_vec();

    use_let();
}

fn use_let() {
    let x = 5;
    let (x, y, z) = (1, 2, 3);
    let (x, _) = (4, 5, 6);
    print_coordinates(&(7, 8));

    fn print_coordinates(&(x, y): &(i32, i32)) {
        println!("Current location: ({},{})", x, y);
    }
}

fn loop_read_from_stack() {
    let mut stack = Vec::new();

    stack.push(1);
    stack.push(2);
    stack.push(3);

    while let Some(top) = stack.pop() {
        println!("{}", top);
    }
}

fn for_read_from_vec() {
    let vec = vec!['a', 'b', 'c'];

    for (index, val) in vec.iter().enumerate() {
        println!("{} is at index {}", val, index);
    }
}
