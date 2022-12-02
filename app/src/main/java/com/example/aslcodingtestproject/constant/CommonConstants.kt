package com.autotoll.ffts.model.constant

object IConstants {

    object DomainName {
        const val API_DOMAIN_ReCaptcha = "https://captcha-dev.atlsmartsolutions.com/"
        const val API_DOMAIN_Google = "https://www.google.com/"
        const val API_DOMAIN_JSONPLACEHOLDER = "https://jsonplaceholder.typicode.com/"
    }

    object BASIC {

        const val BASIC_REQUEST_TIMEOUT = 15000L
        const val SOCKET_CALL_TIMEOUT = 30000L
        const val PAYMENT_REQUEST_TIMEOUT = 30000L
        const val AsymmetricKeyDateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss"
        const val DateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss"
        const val DateTimeFormatNormal = "yyyy-MM-dd'T'HH:mm:ss"
        const val DateTimeFormat_Payment = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val DateTimeFormatSimple = "yyyy-MM-dd HH:mm:ss"
        const val DateTimeFormatShort = "yyyy-MM-dd"

    }

    object RegexValidation {
        const val PasswordRegex =
            "^(?=.*[\\d])(?=.*[A-Z])(?=.*[a-z])(?=.*[\\]\\[!\"\"#\$%&'()*+,./:;<=>?@\\\\^_`{|}~-])(?!.*?[.]{2}).{8,30}\$"
        const val AccountLoginNameRegex =
            "^(?=.*[\\d])(?=.*[A-Za-z])(?!.*?[.]{2})[\\dA-Za-z.]{10,30}\$"
        const val PinRegex = "^\\d{8}\$"
        const val GovEmailRegex = "[A-Z0-9a-z._%+-]+@(\\w+.{1})*gov.hk"
    }

}