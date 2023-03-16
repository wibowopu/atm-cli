package com.example.wsd

import io.micronaut.configuration.picocli.PicocliRunner
import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.Environment
import org.junit.Test
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import java.io.ByteArrayOutputStream
import java.io.PrintStream

class AtmCliCommandSpec extends Specification {

    @Shared @AutoCleanup ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)

    void "test_atm_cli_1_login_Alice"() {
        //"login Alice"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["login", "-n", "Alice"] as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect: "login Alice"
        baos.toString().contains("Hello, Alice!")
        //
    }
    void "test_atm_cli_2_deposit_100"() {
        //deposit 100
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["deposit","-amt","100"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect: "deposit 100"
        baos.toString().contains("Your balance is \$100")
        //
    }
    void "test_atm_cli_2_print_atm_deposit_100"() {
        //"deposit 80"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["list"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains(
                "User{id=1, name='Alice', account=Account{id='null', name='Alice', amount=100.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        ", Owned =Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        "}"
        )
    }
    void "test_atm_cli_3_logout"() {
        //"logout"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["logout"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect: "logout"
        baos.toString().contains("Goodbye, Alice!")
    }

    void "test_atm_cli_4_login_Bob"() {
        //"login Bob"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["login", "-n", "Bob"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains("Hello, Bob!\n" +
                "Your balance is \$0")
    }
    void "test_atm_cli_5_deposit_80"() {
        //"deposit 80"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["deposit", "-amt", "80"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains("Your balance is \$80")
    }
    void "test_atm_cli_5_print_atm_deposit_80"() {
        //"deposit 80"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["list"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains(
                "User{id=1, name='Alice', account=Account{id='null', name='Alice', amount=100.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        ", Owned =Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        "} \n" +
                        "User{id=2, name='Bob', account=Account{id='null', name='Bob', amount=80.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        ", Owned =Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        "}"
        )
    }
    void "test_atm_cli_6_transfer_Alice_50"() {
        //"transfer Alice 50"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["transfer", "-n", "Alice","-amt","50"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains("Transferred \$50 to Alice")
    }
    void "test_atm_cli_6_print_atm_transfer_Alice_50"() {
        //"deposit 80"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["list"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains(
                "User{id=1, name='Alice', account=Account{id='null', name='Alice', amount=150.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        ", Owned =Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        "} \n" +
                        "User{id=2, name='Bob', account=Account{id='null', name='Bob', amount=30.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        ", Owned =Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        "}"
        )
    }
    void "test_atm_cli_7_transfer_Alice_100"() {
        //"transfer Alice 100"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["transfer", "-n", "Alice","-amt","100"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains("Transferred \$30.0 to Alice\n" +
                "Your balance is \$0.0\n" +
                "Owed \$70.0 to Alice")
    }
    void "test_atm_cli_7_print_atm_transfer_Alice_100"() {
        //"deposit 80"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["list"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains(
                "User{id=1, name='Alice', account=Account{id='null', name='Alice', amount=250.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        ", Owned =Holder{id=null, userId=2, name='Bob', amountHold=70.0}\n" +
                        "} \n" +
                        "User{id=2, name='Bob', account=Account{id='null', name='Bob', amount=0.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=1, name='Alice', amountHold=70.0}\n" +
                        ", Owned =Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        "}"
        )
    }
    void "test_atm_cli_8_deposit_30"() {
        //"deposit 80"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["deposit", "-amt", "30"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains(
                "Transferred \$30.0 to Alice\n" +
                "Your balance is \$0.0\n" +
                "Owed \$40.0 to Alice"
        )
    }
    void "test_atm_cli_8_print_atm_deposit_30"() {
        //"deposit 80"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["list"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains(
                "User{id=1, name='Alice', account=Account{id='null', name='Alice', amount=280.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        ", Owned =Holder{id=null, userId=2, name='Bob', amountHold=40.0}\n" +
                        "} \n" +
                        "User{id=2, name='Bob', account=Account{id='null', name='Bob', amount=0.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=1, name='Alice', amountHold=40.0}\n" +
                        ", Owned =Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        "}"
        )
    }
    void "test_atm_cli_9_logout"() {
        //"logout"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["logout"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect: "logout"
        baos.toString().contains("Goodbye, Bob!")
    }

    void "test_atm_cli_10_login_Alice"() {
        //"login Alice"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["login", "-n", "Alice"] as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect: "login Alice"
        baos.toString().contains("Hello, Alice!\n" +
                "Your balance is \$280.0\n" +
                "Owed \$40.0 from Bob")
        //
    }
    void "test_atm_cli_10_print_atm_login_Alice"() {
        //"deposit 80"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["list"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains(
                "User{id=1, name='Alice', account=Account{id='null', name='Alice', amount=280.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        ", Owned =Holder{id=null, userId=2, name='Bob', amountHold=40.0}\n" +
                        "} \n" +
                        "User{id=2, name='Bob', account=Account{id='null', name='Bob', amount=0.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=1, name='Alice', amountHold=40.0}\n" +
                        ", Owned =Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        "}")


    }

    void "test_atm_cli_11_transfer_Bob_30"() {
        //"transfer Alice 100"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["transfer", "-n", "Bob","-amt","30"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains("Transferred \$30.0 to Bob\n" +
                "Your balance is \$280.0\n" +
                "Owed \$10.0 from Bob")
    }

    void "test_atm_cli_11_print_atm_transfer_Bob_30"() {
        //"deposit 80"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["list"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains(
                "User{id=1, name='Alice', account=Account{id='null', name='Alice', amount=280.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        ", Owned =Holder{id=null, userId=2, name='Bob', amountHold=10.0}\n" +
                        "} \n" +
                        "User{id=2, name='Bob', account=Account{id='null', name='Bob', amount=0.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=1, name='Alice', amountHold=10.0}\n" +
                        ", Owned =Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        "}"
        )
    }
    void "test_atm_cli_12_logout"() {
        //"logout"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["logout"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect: "logout"
        baos.toString().contains("Goodbye, Alice!")
    }
    void "test_atm_cli_13_login_Bob"() {
        //"login Bob"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["login", "-n", "Bob"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains(
                "Hello, Bob!\n" +
                        "Your balance is \$0.0\n" +
                        "Owed \$10.0 to Alice")
    }
    void "test_atm_cli_13_print_atm_login_Bob"() {
        //"deposit 80"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["list"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains(
                "User{id=1, name='Alice', account=Account{id='null', name='Alice', amount=280.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        ", Owned =Holder{id=null, userId=2, name='Bob', amountHold=10.0}\n" +
                        "} \n" +
                        "User{id=2, name='Bob', account=Account{id='null', name='Bob', amount=0.0, transactions=[]}\n" +
                        ", Holder=Holder{id=null, userId=1, name='Alice', amountHold=10.0}\n" +
                        ", Owned =Holder{id=null, userId=null, name='null', amountHold=0.0}\n" +
                        "}"
        )
    }
    void "test_atm_cli_14_deposit_100"() {
        //deposit 100
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["deposit","-amt","100"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect:
        baos.toString().contains(
                "Transferred \$10.0 to Alice\n" +
                "Your balance is \$90.0")
        //
    }
    void "test_atm_cli_15_logout"() {
        //"logout"
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream out = System.out
        System.setOut(new PrintStream(baos))

        String[] args = ["logout"]  as String[]
        PicocliRunner.run(AtmCliCommand, ctx, args)
        out.println baos.toString()

        expect: "logout"
        baos.toString().contains("Goodbye, Bob!")
    }
}

