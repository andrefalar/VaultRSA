package com.andrefalar.vaultrsa.ui.vault

data class VaultItem (
    val siteName: String,
    val userName: String,
    var password: String
) {
    constructor() : this("", "", "")
}
