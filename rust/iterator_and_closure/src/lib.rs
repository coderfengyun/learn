#[cfg(test)]
mod tests {
    use crate::{shoes_in_my_size, Shoe};

    #[test]
    pub fn test_iterator_next() {
        let v1 = vec![1, 2, 3];
        let mut iter = v1.iter();
        assert_eq!(iter.next(), Some(&1));
        assert_eq!(iter.next(), Some(&2));
        assert_eq!(iter.next(), Some(&3));
        assert_eq!(iter.next(), None);
    }

    #[test]
    pub fn test_iterator_sum() {
        let v1 = vec![1, 2, 3];
        let iter = v1.iter();

        let sum: i32 = iter.sum();
        assert_eq!(sum, 6);
    }

    #[test]
    pub fn test_iterator_map() {
        let v1 = vec![1, 2, 3];
        let iter = v1.iter();
        let v2: Vec<_> = iter.map(|x| x + 1).collect();
        assert_eq!(v2, vec![2, 3, 4]);
    }

    #[test]
    pub fn test_shoes_in_my_size() {
        let vec1 = vec![Shoe {
            size: 1,
            style: "black".to_string(),
        }];
        let result = shoes_in_my_size(&vec1, 1);
        assert_eq!(
            result,
            vec![&Shoe {
                size: 1,
                style: "black".to_string(),
            }]
        );
        println!("{:?}", vec1.iter().next());
    }
}

#[derive(Debug, PartialEq)]
struct Shoe {
    pub size: u32,
    style: String,
}

fn shoes_in_my_size(shoes: &Vec<Shoe>, size: u32) -> Vec<&Shoe> {
    shoes.into_iter().filter(|s| s.size == size).collect()
}

struct Counter {
    count: u32,
}

impl Counter {
    pub fn new() -> Counter {
        Counter { count: 0 }
    }
}

impl Iterator for Counter {
    type Item = u32;

    fn next(&mut self) -> Option<Self::Item> {
        if self.count < 5 {
            self.count += 1;
            Some(self.count)
        } else {
            None
        }
    }
}

mod counter_tests {
    use super::*;

    #[test]
    pub fn test_next() {
        let mut counter = Counter::new();
        assert_eq!(counter.next(), Some(1));
        assert_eq!(counter.next(), Some(2));
        assert_eq!(counter.next(), Some(3));
        assert_eq!(counter.next(), Some(4));
        assert_eq!(counter.next(), Some(5));
        assert_eq!(counter.next(), None);
        assert_eq!(counter.next(), None);
    }

    #[test]
    fn test_use_another_counter_trait() {
        let result: u32 = Counter::new()
            .zip(Counter::new().skip(1))
            .map(|(x, y)| x * y)
            .filter(|x| x % 3 == 0)
            .sum();
        assert_eq!(18, result);
    }
}
