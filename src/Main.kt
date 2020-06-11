import java.lang.NumberFormatException
import java.math.BigDecimal

fun getBank() : Bank {
    return Bank("Bank of the Universe")
}

fun createAccount(bank: Bank) {
    println("Create new account for ${bank.name}")

    print("Name: ")
    val name = readLine()!!
    val account = Account(name)
    bank.addAccount(account)

    println("Account ${account.name} with id ${account.id} created")
}

fun deposit(bank: Bank) {
    print("Account id: ")
    val accountId = try { readLine()!!.toInt() } catch (e: NumberFormatException) { println("Invalid int") }
    print("Amount: ")
    val amount = try { readLine()!!.toBigDecimal() } catch (e: NumberFormatException) { println("Invalid currency") }

    if (accountId is Int && amount is BigDecimal) {
        val account = bank.accounts[accountId]

        if (account != null) {
            account.deposit(amount)
            println("Deposited $amount! Balance is now ${account.balance}")
        }
    }
}

fun main() {
    val bank = getBank()
    println("Welcome to da '${bank.name}'!")

    while (true) {
        print("Options:\n1) Create new account\n2) Deposit\n3) Withdraw\n> ")

        when (try { readLine()!!.toInt() } catch (e: NumberFormatException) { println("Invalid int") }) {
            1 -> createAccount(bank)
            2 -> deposit(bank)
        }
    }
}
