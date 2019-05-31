package com.rabbitcat.authorizeSever.enum

enum class ProviderType(val clientId: String) {
    FACEBOOK("351838902128557"), GITHUB("bd1c0a783ccdd1c9b9e4"), KAKAO("21fcbad5b90970cd7557d91d59d6d99f");

    companion object {
        fun from(clientId: String): ProviderType = ProviderType.values().first { clientId == it.clientId }
    }
}