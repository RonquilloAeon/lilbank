import java.math.BigDecimal

class Account(var id: Int = 0, val name: String, var balance: BigDecimal = BigDecimal("0.00")) {
    init {
        if(id == 0) {
            id = kotlin.random.Random.nextInt(1, 100000)
        }
    }

    fun deposit(amount: BigDecimal) {
        balance = balance.add(amount)
    }

    fun withdraw(amount: BigDecimal) {
        balance = balance.subtract(amount)
    }

    fun printInfo() {
        println("Account $id: $name ($$balance)")
    }
}

class Bank(val name: String) {
    var accounts = mutableMapOf<Int, Account>()

    fun addAccount(account: Account) {
        accounts[account.id] = account
    }

    fun removeAccount(id: Int) {
        accounts.remove(id)
    }
}
