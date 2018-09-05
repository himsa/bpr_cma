package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class PasivaHutang {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("analisa_keuangan_id")
    @Expose
    private var analisaKeuanganId: Int? = null
    @SerializedName("bank")
    @Expose
    private var bank: Int? = null
    @SerializedName("dagang")
    @Expose
    private var dagang: Int? = null
    @SerializedName("jangka_panjang")
    @Expose
    private var jangkaPanjang: Int? = null
    @SerializedName("kewajiban_membayar")
    @Expose
    private var kewajibanMembayar: Int? = null
    @SerializedName("sub_jumlah")
    @Expose
    private var subJumlah: Int? = null
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

    fun getBank(): Int? {
        return bank
    }

    fun setBank(bank: Int?) {
        this.bank = bank
    }

    fun getDagang(): Int? {
        return dagang
    }

    fun setDagang(dagang: Int?) {
        this.dagang = dagang
    }

    fun getJangkaPanjang(): Int? {
        return jangkaPanjang
    }

    fun setJangkaPanjang(jangkaPanjang: Int?) {
        this.jangkaPanjang = jangkaPanjang
    }

    fun getKewajibanMembayar(): Int? {
        return kewajibanMembayar
    }

    fun setKewajibanMembayar(kewajibanMembayar: Int?) {
        this.kewajibanMembayar = kewajibanMembayar
    }

    fun getSubJumlah(): Int? {
        return subJumlah
    }

    fun setSubJumlah(subJumlah: Int?) {
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