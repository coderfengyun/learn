#![recursion_limit = "256"]


mod ook {



    macro_rules! Ook {

    (@start $($Ooks:tt)*) => {
            {
                type CellType = u8;
                const MEM_SIZE: usize = 30_000;

                fn ook() -> ::std::io::Result<Vec<CellType>> {
                    use ::std::io;
                    use ::std::io::prelude::*;

                    fn _re() -> io::Error {
                        io::Error::new(
                            io::ErrorKind::Other,
                            String::from("ran out of input")
                        )
                    }

                    fn _inc(a: &mut [u8], i: usize) {
                        let c = &mut a[i];
                        *c = c.wrapping_add(1);
                    }

                    fn _dec(a: &mut [u8], i: usize) {
                        let c = &mut a[i];
                        *c = c.wrapping_sub(1);
                    }

                    let _r = &mut io::stdin();
                    let _w = &mut io::stdout();

                    let mut _a: Vec<CellType> = Vec::with_capacity(MEM_SIZE);
                    _a.extend(::std::iter::repeat(0).take(MEM_SIZE));

                    let mut _i = 0;
                    {
                        let _a = &mut *_a;
                        Ook!(@e (_a, _i, _inc, _dec, _r, _w, _re); ($($Ooks)*));
                    }

                    Ok(_a)
                }

                ook()
            }
        };

        (@e $syms:tt; ()) => {
        };

        (@e ($a:expr, $i:expr, $inc:expr, $dec:expr, $r:expr, $w:expr, $re:expr); (Ook. Ook? $($tail:tt)*)) => {
            $i = ($i + 1) % MEM_SIZE;
            log_syntax!(current tail in OoK. Ook? is: $($tail)*);
            Ook!(@e ($a,$i, $inc, $dec, $r, $w, $re); ($($tail)*));
        };

        (@e ($a:expr, $i:expr, $inc:expr, $dec:expr, $r:expr, $w:expr, $re:expr); (Ook? Ook. $($tail:tt)*)) => {
            $i = if $i == 0 { MEM_SIZE } else { $i - 1 };
            log_syntax!(current tail in OoK? Ook. is: $($tail)*);
            Ook!(@e ($a, $i, $inc, $dec, $r, $w, $re); ($($tail)*));
        };

        (@e ($a:expr, $i:expr, $inc:expr, $dec:expr, $r:expr, $w:expr, $re:expr); (Ook. Ook. $($tail:tt)*)) => {
            $inc($a, $i);
            log_syntax!(current tail in Ook. Ook. is: $($tail)*);
            Ook!(@e ($a, $i, $inc, $dec, $r, $w, $re); ($($tail)*));
        };

        (@e ($a:expr, $i:expr, $inc:expr, $dec:expr, $r:expr, $w:expr, $re:expr); (Ook! Ook! $($tail:tt)*)) => {
            $dec($a, $i);
            Ook!(@e ($a, $i, $inc, $dec, $r, $w, $re); ($($tail)*));
        };

        // Write to stdout.
        (@e ($a:expr, $i:expr, $inc:expr, $dec:expr, $r:expr, $w:expr, $re:expr); (Ook! Ook. $($tail:tt)*)) => {
            r#try!($w.write_all(&$a[$i .. $i+1]));
            Ook!(@e ($a, $i, $inc, $dec, $r, $w, $re); ($($tail)*));
        };

        // Read from stdin.
        (@e ($a:expr, $i:expr, $inc:expr, $dec:expr, $r:expr, $w:expr, $re:expr); (Ook. Ook! $($tail:tt)*)) => {
            r#try!(
                match $r.read(&mut a[$i .. $i+1]) {
                    Ok(0) => Error($re()),
                    ok @ Ok(..) => ok,
                    err @ Err(..) => err,
                }
                Ook!(@e ($a, $i, $inc, $dec, $r, $w, $re); ($($tail)*));
            )
        };

        // parse while
        (@e ($a:expr, $i:expr, $inc:expr, $dec:expr, $r:expr, $w:expr, $re:expr); (Ook! Ook? $($tail:tt)*)) => {
            while $a[$i] != 0 {
                log_syntax!(current tail in Ook! Ook? is: $($tail)*);
                Ook!(@x ($a, $i, $inc, $dec, $r, $w, $re); (); (); ($($tail)*));
            }
            Ook!(@s ($a, $i, $inc, $dec, $r, $w, $re); (); ($($tail)*));
        };

        // parse outer most loop
        (@x $syms:tt; (); ($($buf:tt)*); (Ook? Ook! $($tail:tt)*)) => {
            // Outer-most loop is closed. Process the buffered tokens.
            log_syntax!(current buf in outer most loop is: $($buf)*);
            log_syntax!(current tail in outer most loop is: $($tail)*);
            Ook!(@e $syms; ($($buf)*));
        };

        (@x $syms:tt; ($($depth:tt)*); ($($buf:tt)*); (Ook! Ook? $($tail:tt)*)) => {
            //One level deeper
            Ook!(@x $syms; (@ $($depth)*); ($($buf)*; Ook! Ook?); $($tail)* );
        };

        (@x $syms:tt; (@ $($depth:tt)*); ($($buf:tt)*); (Ook? Ook! $($tail:tt)*)) => {
            //One level higher
            Ook!(@x $syms; ($($depth)*); ($($buf)* Ook? Ook!); ($($tail)*));
        };

        (@x $syms:tt; $depth:tt; ($($buf:tt)*); (Ook $op0:tt Ook $op1:tt $($tail:tt)*)) => {
            //everything else
            log_syntax!(current tail in everything else is: $($tail)*);
            Ook!(@x $syms; $depth; ($($buf)* Ook $op0 Ook $op1); ($($tail)*));
        };

        (@s $syms:tt; (); (Ook? Ook! $($tail:tt)*)) => {
            //End of loop
            log_syntax!(current tail @s in end of loop is: $($tail)*);
            Ook!(@e $syms; ($($tail)*));
        };

        // Enter nested loops
        (@s $syms:tt; ($($depth:tt)*); (Ook! Ook? $($tail:tt)*)) => {
            Ook!(@s $syms; (@ $($depth)*); ($($tail)*));
        };

        // Exit nested loops
        (@s $syms:tt; (@ $($depth:tt)*); (Ook? Ook! $($tail:tt)*)) => {
            Ook!(@s $syms; ($($depth)*); ($($tail)*));
        };

        // Not a loop opcode
        (@s $syms:tt; ($($depth:tt)*); (Ook $op0:tt Ook $op1:tt $($tail:tt)*)) => {
            log_syntax!(current tail @s not a loop opcode is: $($tail)*);
            Ook!(@s $syms; ($($depth)*); ($($tail)*));
        };

        //entry point
        ($($Ooks:tt)*) => {
            Ook!(@start $($Ooks)*);
        };
    }

    mod usage {
        pub fn use1() {
            // Ook!(Ook. Ook.);
            // Ook!(Ook. Ook? Ook. Ook.);
            // Ook!(Ook. Ook? Ook. Ook. Ook. Ook.  Ook. Ook.
            //         Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook.
            //         Ook. Ook. Ook. Ook. Ook! Ook? Ook? Ook.
            //         Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook.
            //         Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook.
            //         Ook. Ook? Ook! Ook! Ook? Ook!);
            Ook!(
              Ook. Ook? Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook.  Ook. Ook.  Ook! Ook?  Ook? Ook.
        Ook. Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook?  Ook! Ook!  Ook? Ook!  Ook? Ook.
        Ook! Ook.  Ook. Ook?  Ook. Ook.  Ook. Ook.
        Ook. Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook.  Ook! Ook?  Ook? Ook.  Ook. Ook.
        Ook. Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook?
        Ook! Ook!  Ook? Ook!  Ook? Ook.  Ook. Ook.
        Ook! Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook! Ook.  Ook! Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook.  Ook! Ook.  Ook. Ook?  Ook. Ook?
        Ook. Ook?  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook.  Ook! Ook?  Ook? Ook.  Ook. Ook.
        Ook. Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook?
        Ook! Ook!  Ook? Ook!  Ook? Ook.  Ook! Ook.
        Ook. Ook?  Ook. Ook?  Ook. Ook?  Ook. Ook.
        Ook. Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook.  Ook! Ook?  Ook? Ook.  Ook. Ook.
        Ook. Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook?  Ook! Ook!  Ook? Ook!  Ook? Ook.
        Ook! Ook!  Ook! Ook!  Ook! Ook!  Ook! Ook.
        Ook? Ook.  Ook? Ook.  Ook? Ook.  Ook? Ook.
        Ook! Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook! Ook.  Ook! Ook!  Ook! Ook!  Ook! Ook!
        Ook! Ook!  Ook! Ook!  Ook! Ook!  Ook! Ook.
        Ook! Ook!  Ook! Ook!  Ook! Ook!  Ook! Ook!
        Ook! Ook!  Ook! Ook!  Ook! Ook!  Ook! Ook!
        Ook! Ook.  Ook. Ook?  Ook. Ook?  Ook. Ook.
        Ook! Ook.  Ook! Ook?  Ook! Ook!  Ook? Ook!
        Ook. Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook.  Ook. Ook.  Ook. Ook.  Ook. Ook.
        Ook. Ook.  Ook. Ook.  Ook! Ook.
            );
        }

        #[test]
        pub fn use1_test() {
            let res = use1();
            println!("{:?}", res);
        }
    }
}