# atm-cli
atm cli

# ATM

## Extract File atm-cli-0.1-all.zip

please extract your own path folder. with this file zip 
file : atm-cli-0.1-all.zip
example path : C:\workspace

## Run Program
## Open Windows Commands Prompt, on your path folder extract file above
* klik windows + R , type cmd.exe
* type cd c:\workspace
* type c:\workspace>atm-cli
  output 
	[atm-cli] welcome in atm CLI


* type c:\workspace>atm-cli --help

## Login Command
* type c:\workspace>atm-cli login --help

## Deposit Command
* type c:\workspace>atm-cli deposit --help

## Transfer Command
* type c:\workspace>atm-cli transfer --help

## Logout Command
* type c:\workspace>atm-cli logout --help




Your can use this scenario and commands. 

```command prompt
c:\workspace>atm-cli login -n Alice
Hello, Alice!
Your balance is $0

c:\workspace>atm-cli deposit -amt 100
Your balance is $100

c:\workspace>atm-cli logout
Goodbye, Alice!

c:\workspace>atm-cli login -n Bob
Hello, Bob!
Your balance is $0

c:\workspace>atm-cli deposit -amt 80
Your balance is $80

c:\workspace>atm-cli transfer -n Alice -amt 50
Transferred $50 to Alice
your balance is $30

c:\workspace>atm-cli transfer -n Alice -amt 100
Transferred $30 to Alice
Your balance is $0
Owed $70 to Alice

c:\workspace>atm-cli deposit -amt 30
Transferred $30 to Alice
Your balance is $0
Owed $40 to Alice

c:\workspace>atm-cli logout
Goodbye, Bob!

c:\workspace>atm-cli login -n Alice
Hello, Alice!
Your balance is $280   
Owed $40 from Bob

c:\workspace>atm-cli transfer -n Bob -amt 30
Your balance is $280   
Owed $10 from Bob

c:\workspace>atm-cli logout
Goodbye, Alice!

c:\workspace>atm-cli login -n Bob
Hello, Bob!
Your balance is $0
Owed $10 to Alice

c:\workspace>atm-cli deposit -amt 100
Transferred $10 to Alice  //Done
Your balance is $90

c:\workspace>atm-cli logout
Goodbye, Bob!
```
