use std::error::Error;
use std::fs;

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    pub fn one_result() {
        let query = "duct";
        let contents = "Rust:\n\
        safe, fast, productive.\n
        Pick three.";
        assert_eq!(vec!["safe, fast, productive."], search(query, contents));
    }
}

pub fn search<'a>(query: &str, contents: &'a str) -> Vec<&'a str> {
    let mut result = Vec::new();

    for line in contents.lines() {
       if line.contains(query) {
            result.push(line);
       }
    }
    result
}

pub fn run(config: Config) -> Result<(), Box<dyn Error>> {
    println!(
        "searchString: {}, filePath: {}",
        config.query, config.filename
    );

    let result = fs::read_to_string(&config.filename)?;

    println!("config: {:?}, file content is :{}", &config, result);
    Ok(())
}

#[derive(Debug)]
pub struct Config {
    query: String,
    filename: String,
}

impl Config {
    pub fn new(args: &[String]) -> Result<Config, &'static str> {
        if args.len() < 3 {
            return Err("no enough parameters!");
        }

        let query = args[1].clone();

        let filename = args[2].clone();

        Ok(Config { query, filename })
    }
}
