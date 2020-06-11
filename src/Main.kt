import java.lang.NumberFormatException
import java.math.BigDecimal

fun main() {
    var account = Account("Martin")
    var bank = Bank("Bank of the Universe")
    println("Welcome to da '${bank.name}'!")

    while (true) {
        print("Enter an amount to deposit ")
        val amount = try { readLine()!!.toBigDecimal() } catch (e: NumberFormatException) { println("Invalid currency") }

        if (amount is BigDecimal) {
            account.deposit(amount)
            println("Deposited $amount, account now has ${account.balance}")
        }
    }
}
