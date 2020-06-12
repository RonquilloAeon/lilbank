package lilbank.adapters

import java.io.File
import java.io.StringReader
import java.math.BigDecimal

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon

import Account

data class AccountDto (val id: Int, val name: String, val balance: String)

class AccountRepository {
    private val fileName = "accounts.json"

    private fun loadFile() : MutableMap<Int, Account> {
        val file = File(fileName).readText()
        val klaxon = Klaxon()
        val parsed = klaxon.parseJsonObject(StringReader(file))
        val accounts = mutableMapOf<Int, Account>()

        parsed.map{ it.value as JsonObject }.forEach {
            val acctDto = klaxon.parseFromJsonObject<AccountDto>(it)
            if (acctDto != null) {
                accounts[acctDto.id] = Account(acctDto.id, acctDto.name, BigDecimal(acctDto.balance))
            }
        }

        return accounts
    }

    fun get(id: Int) : Account? {
        val accounts = loadFile()
        return accounts.get(id)
    }

    fun list() : MutableList<Account> {
        var accountList = mutableListOf<Account>()
        accountList.addAll(loadFile().values.map { x -> x })
        return accountList
    }

    fun save() {

    }
}
