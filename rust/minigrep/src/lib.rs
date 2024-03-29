use std::error::Error;
use std::fs;
use std::env;

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    pub fn case_sensitive() {
        let query = "duct";
        let contents = "Rust:\n\
        safe, fast, productive.\n
        Pick three.\n
        Duct tape.";
        assert_eq!(vec!["safe, fast, productive."], search(query, contents));
    }

    #[test]
    fn case_insensitive() {
        let query = "rUsT";
        let contents = "Rust:\n\
        safe, fast, productive.\n
        Pick three.\n\
        Trust me.";
        assert_eq!(
            vec!["Rust:", "Trust me."],
            search_case_insensitive(query, contents)
        );
    }
}

fn search_case_insensitive<'a>(query: &str, contents: &'a str) -> Vec<&'a str> {

    let query = &query.to_lowercase();
    contents.lines().filter(|line| line.to_lowercase().contains(query)).collect()
}

pub fn search<'a>(query: &str, contents: &'a str) -> Vec<&'a str> {
    contents.lines().filter(|line| line.contains(query)).collect()
}

pub fn run(config: Config) -> Result<(), Box<dyn Error>> {

    let contents = fs::read_to_string(&config.filename)?;

    let result = if config.case_sensitive {
        search(&config.query, &contents)
    } else {
        search_case_insensitive(&config.query, &contents)
    };

    for line in result {
        println!("{}", line);
    }

    Ok(())
}

#[derive(Debug)]
pub struct Config {
    query: String,
    filename: String,
    case_sensitive: bool,
}

impl Config {

    pub fn new(mut args: env::Args) -> Result<Config, &'static str> {

        args.next();

        let query = match args.next() {
            Some(s) => s,
            None => return Err("Didn't get a query string")
        };
        let filename = match args.next() {
            Some(s) => s,
            None => return Err("Didn't get a filename string")
        };

        let case_sensitive = env::var("CASE_INSENSITIVE").is_err();

        println!("query: {}, filename: {}, case_sensitive: {}", query, filename, case_sensitive);

        Ok(Config { query, filename, case_sensitive})
    }
}
