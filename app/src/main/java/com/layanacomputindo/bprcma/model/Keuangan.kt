package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Keuangan {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("periode")
    @Expose
    private var periode: String? = null
    @SerializedName("total_aktiva")
    @Expose
    private var totalAktiva: Int? = null
    @SerializedName("total_pasiva")
    @Expose
    private var totalPasiva: Int? = null
    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null
    @SerializedName("kredit_id")
    @Expose
    private var kreditId: Int? = null
    @SerializedName("aktiva_lancar")
    @Expose
    private var aktivaLancar: AktivaLancar? = null
    @SerializedName("aktiva_tetap")
    @Expose
    private var aktivaTetap: AktivaTetap? = null
    @SerializedName("pasiva_hutang")
    @Expose
    private var pasivaHutang: PasivaHutang? = null
    @SerializedName("pasiva_modal")
    @Expose
    private var pasivaModal: PasivaModal? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getPeriode(): String? {
        return periode
    }

    fun setPeriode(periode: String) {
        this.periode = periode
    }

    fun getTotalAktiva(): Int? {
        return totalAktiva
    }

    fun setTotalAktiva(totalAktiva: Int?) {
        this.totalAktiva = totalAktiva
    }

    fun getTotalPasiva(): Int? {
        return totalPasiva
    }

    fun setTotalPasiva(totalPasiva: Int?) {
        this.totalPasiva = totalPasiva
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

    fun getKreditId(): Int? {
        return kreditId
    }

    fun setKreditId(kreditId: Int?) {
        this.kreditId = kreditId
    }

    fun getAktivaLancar(): AktivaLancar? {
        return aktivaLancar
    }

    fun setAktivaLancar(aktivaLancar: AktivaLancar) {
        this.aktivaLancar = aktivaLancar
    }

    fun getAktivaTetap(): AktivaTetap? {
        return aktivaTetap
    }

    fun setAktivaTetap(aktivaTetap: AktivaTetap) {
        this.aktivaTetap = aktivaTetap
    }

    fun getPasivaHutang(): PasivaHutang? {
        return pasivaHutang
    }

    fun setPasivaHutang(pasivaHutang: PasivaHutang) {
        this.pasivaHutang = pasivaHutang
    }

    fun getPasivaModal(): PasivaModal? {
        return pasivaModal
    }

    fun setPasivaModal(pasivaModal: PasivaModal) {
        this.pasivaModal = pasivaModal
    }

}