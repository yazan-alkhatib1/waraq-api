package com.waraq.enums;

public enum CurrencyCodes {

    AED("UAE Dirham"), ALL("Albanian Lek"), AMD("Armenian Dram"),
    ANG("Antillian Guilder"), AOA("Angolan Kwanza"), ARS("Nuevo Argentine Peso"),
    AUD("Australian Dollar"), AWG("Aruban Guilder"), AZN("Azerbaijani Manat"),
    BAM("Bosnia and Herzegovina Convertible Marks"), BBD("Barbados Dollar"), BDT("Bangladesh Taka"),
    BGN("New Bulgarian Lev"), BHD("Bahraini Dinar"), BMD("Bermudian Dollar"),
    BND("Brunei Dollar"), BOB("Bolivia Boliviano"), BRL("Brazilian Real"),
    BSD("Bahamian Dollar"), BWP("Botswana Pula"), BYN("New Belarusian Ruble"),
    BZD("Belize Dollar"), BIF("Burundian Franc"), CLF("Chilean Unidad de Fomentos"), CAD("Canadian Dollar"), CHF("Swiss Franc"),
    CLP("Chilean Peso"), CNY("Yuan Renminbi"), COP("Colombian Peso"),
    CRC("Costa Rican Colon"), CUP("Cuban Peso"), CVE("Cape Verdi Escudo"),
    CZK("Czech Koruna"), DJF("Djibouti Franc"), DKK("Danish Krone"),
    DOP("Dominican Republic Peso"), DZD("Algerian Dinar"), EGP("Egyptian Pound"),
    ETB("Ethiopian Birr"), EUR("Euro"), FJD("Fiji Dollar"),
    FKP("Falkland Islands Pound"), GBP("Pound Sterling"), GEL("Georgian Lari"),
    GHS("Ghanaian Cedi (3rd)"), GIP("Gibraltar Pound"), GMD("Gambia Delasi"),
    GNF("Guinea Franc"), GTQ("Guatemala Quetzal"), GYD("Guyanese Dollar"),
    HKD("Hong Kong Dollar"), HNL("Honduras Lempira"), HTG("Haitian Gourde"),
    HUF("Hungarian Forint"), IDR("Indonesian Rupiah"),
    INR("Indian Rupee"), IQD("Iraqi Dinar"), ISK("Iceland Krona"),
    JMD("Jamaican Dollar"), JOD("Jordanian Dinar"), JPY("Japanese Yen"),
    KES("Kenyan Shilling"), KGS("Kyrgyzstan Som"), KHR("Cambodian Riel"),
    KMF("Comoro Franc"), KRW("South-Korean Won"), KWD("Kuwaiti Dinar"),
    KYD("Cayman Islands Dollar"), KZT("Kazakhstani Tenge"), LAK("Laos Kip"),
    LBP("Lebanese Pound"), LKR("Sri Lanka Rupee"), LYD("Libyan Dinar"),
    MAD("Moroccan Dirham"), MDL("Moldovan Leu"), MKD("Macedonian Denar"),
    MMK("Myanmar Kyat"), MNT("Mongolia Tugrik"), MOP("Macau Pataca"),
    MRU("Mauritania Ouguiya"), MUR("Mauritius Rupee"), MVR("Maldives Rufiyaa"),
    MWK("Malawi Kwacha"), MXN("Mexican Peso"), MYR("Malaysian Ringgit"),
    MZN("Mozambican Metical"), NAD("Namibian Dollar"), NGN("Nigerian Naira"),
    NIO("Nicaragua Cordoba Oro"), NOK("Norwegian Krone"), NPR("Nepalese Rupee"),
    NZD("New Zealand Dollar"), OMR("Rial Omani"), PAB("Panamanian Balboa"),
    PEN("Peruvian Nuevo Sol"), PGK("New Guinea Kina"), PHP("Philippine Peso"),
    PKR("Pakistan Rupee"), PLN("New Polish Zloty"), PYG("Paraguay Guarani"),
    QAR("Qatari Rial"), RON("New Romanian Lei"), RSD("Serbian Dinar"),
    RUB("Russian Ruble"), RWF("Rwanda Franc"), SAR("Saudi Riyal"),
    SBD("Solomon Island Dollar"), SCR("Seychelles Rupee"), SEK("Swedish Krona"),
    SGD("Singapore Dollar"), SHP("St. Helena Pound"), SLE("Sierra Leone Leone"),
    SOS("Somalia Shilling"), SRD("Surinamese Dollar"), STN("Sao Tome & Principe Dobra"),
    SVC("El Salvador Colón"), SZL("Swaziland Lilangeni"), THB("Thai Baht"),
    TND("Tunisian Dinar"), TOP("Tongan Pa'anga"), TRY("New Turkish Lira"),
    TTD("Trinidad & Tobago Dollar"), TWD("New Taiwan Dollar"), TZS("Tanzanian Shilling"),
    UAH("Ukraine Hryvnia"), UGX("Uganda Shilling"), USD("US Dollars"),
    UYU("Peso Uruguayo"), UZS("Uzbekistani Som"), VEF("Venezuelan Bolívar"),
    VND("Vietnamese New Dong"), VUV("Vanuatu Vatu"), WST("Samoan Tala"),
    XAF("CFA Franc BEAC"), XCD("East Caribbean Dollar"), XOF("CFA Franc BCEAO"),
    XPF("CFP Franc"), YER("Yemeni Rial"), ZAR("South African Rand"),
    ZMW("Zambian Kwacha");

    private final String name;

    CurrencyCodes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
