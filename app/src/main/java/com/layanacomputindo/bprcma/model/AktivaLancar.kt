package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class AktivaLancar {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("analisa_keuangan_id")
    @Expose
    private var analisaKeuanganId: Int? = null
    @SerializedName("kas")
    @Expose
    private var kas: Any? = null
    @SerializedName("bank")
    @Expose
    private var bank: Any? = null
    @SerializedName("piutang")
    @Expose
    private var piutang: Any? = null
    @SerializedName("persediaan")
    @Expose
    private var persediaan: Any? = null
    @SerializedName("sub_jumlah")
    @Expose
    private var subJumlah: Any? = null
    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getAnalisaKeuanganId(): Int? {
        return analisaKeuanganId
    }

    fun setAnalisaKeuanganId(analisaKeuanganId: Int?) {
        this.analisaKeuanganId = analisaKeuanganId
    }

    fun getKas(): Any? {
        return kas
    }

    fun setKas(kas: Any) {
        this.kas = kas
    }

    fun getBank(): Any? {
        return bank
    }

    fun setBank(bank: Any) {
        this.bank = bank
    }

    fun getPiutang(): Any? {
        return piutang
    }

    fun setPiutang(piutang: Any) {
        this.piutang = piutang
    }

    fun getPersediaan(): Any? {
        return persediaan
    }

    fun setPersediaan(persediaan: Any) {
        this.persediaan = persediaan
    }

    fun getSubJumlah(): Any? {
        return subJumlah
    }

    fun setSubJumlah(subJumlah: Any) {
        this.subJumlah = subJumlah
    }

    fun getCreatedAt(): String? {
        return createdAt
    }

    fun setCreatedAt(createdAt: String) {
        this.createdAt = createdAt
    }

    fun getUpdatedAt(): String? {
        return updatedAt
    }

    fun setUpdatedAt(updatedAt: String) {
        this.updatedAt = updatedAt
    }
}