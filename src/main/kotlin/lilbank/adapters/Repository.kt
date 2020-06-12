package lilbank.adapters

import Account
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.StringReader
import java.math.BigDecimal


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

    private fun saveFile(content: String) {
        val writer = BufferedWriter(FileWriter(fileName))
        writer.write(content)
        writer.close()
    }

    private fun serializeDataToJson(accounts: MutableMap<Int, Account>) : String {
        val data = JsonObject()

        for (acct: Account in accounts.values) {
            data.put(
                    acct.id.toString(),
                    JsonObject(mapOf(Pair("id", acct.id), Pair("name", acct.name), Pair("balance", acct.balance.toString())))
            )
        }

        return data.toJsonString()
    }

    fun delete(id: Int) {
        val accounts = loadFile()
        val account = accounts.get(id)

        if (account != null) {
            if (account.balance.compareTo(BigDecimal.ZERO) == 0) {
                accounts.remove(id)

                saveFile(serializeDataToJson(accounts))
                println("Removed account $id")
            } else {
                println("Account $id cannot be removed because it has a balance of ${account.balance}")
            }
        }
    }

    fun get(id: Int) : Account? {
        val accounts = loadFile()
        return accounts.get(id)
    }

    fun list() : MutableList<Account> {
        val accountList = mutableListOf<Account>()
        accountList.addAll(loadFile().values.map { x -> x })
        return accountList
    }

    fun save(account: Account) {
        val accounts = loadFile()
        accounts[account.id] = account

        saveFile(serializeDataToJson(accounts))
    }
}
