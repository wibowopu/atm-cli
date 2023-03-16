# ATM

## Problem Statement

You are asked to develop a Command Line Interface (CLI) to simulate an interaction of an ATM with a retail bank.

## Commands

* `login [name]` - Logs in as this customer and creates the customer if not exist
* `deposit [amount]` - Deposits this amount to the logged in customer
* `withdraw [amount]` - Withdraws this amount from the logged in customer
* `transfer [target] [amount]` - Transfers this amount from the logged in customer to the target customer
* `logout` - Logs out of the current customer

## Example Session

Your console output should contain at least the following output depending on the scenario and commands. But feel free 
to add extra output as you see fit.

```bash
$ login Alice
Hello, Alice!
Your balance is $0
Done

$ deposit 100
Your balance is $100
Done

$ logout
Goodbye, Alice!
Done

$ login Bob
Hello, Bob!
Your balance is $0
Done 

$ deposit 80
Your balance is $80
Done



$ transfer Alice 50
Transferred $50 to Alice
your balance is $30
Done



$ transfer Alice 100
Transferred $30 to Alice
Your balance is $0
Owed $70 to Alice
done

////////////////////////////
$ deposit 30              Ke BOB
Transferred $30 to Alice
Your balance is $0
Owed $40 to Alice
Done
///////////////////////
$ logout
Goodbye, Bob!
Done

$ login Alice
Hello, Alice!
Your balance is $210  ////////////$280
Owed $40 from Bob
Done


//////////////////On Progress
$ transfer Bob 30
Your balance is $210  /////$280
Owed $10 from Bob

$ logout
Goodbye, Alice!

$ login Bob
Hello, Bob!
Your balance is $0
Owed $10 to Alice

$ deposit 100
Transferred $10 to Alice
Your balance is $90

$ logout
Goodbye, Bob!
```
