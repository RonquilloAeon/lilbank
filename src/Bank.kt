import java.math.BigDecimal

class Account(val name: String) {
    var id: Int = 0
    var balance: BigDecimal = BigDecimal("0.00")

    init {
        id = kotlin.random.Random.nextInt(1, 100000)
    }

    fun deposit(amount: BigDecimal) {
        balance = balance.add(amount)
    }

    fun withdraw(amount: BigDecimal) {
        balance = balance.subtract(amount)
    }
}

class Bank(val name: String) {
    var accounts = mutableMapOf<Int, Account>()

    fun addAccount(account: Account) {
        accounts[account.id] = account
    }
}
