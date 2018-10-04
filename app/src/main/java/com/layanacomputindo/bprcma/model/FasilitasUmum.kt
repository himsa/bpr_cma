package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class FasilitasUmum {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("jaminan_tanah_bangunan_id")
    @Expose
    private var jaminanTanahBangunanId: Int? = null
    @SerializedName("angkutan_umum")
    @Expose
    private var angkutanUmum: Int? = null
    @SerializedName("pasar")
    @Expose
    private var pasar: Int? = null
    @SerializedName("sekolah")
    @Expose
    private var sekolah: Int? = null
    @SerializedName("rumah_sakit_puskesmas")
    @Expose
    private var rumahSakitPuskesmas: Int? = null
    @SerializedName("hiburan")
    @Expose
    private var hiburan: Int? = null
    @SerializedName("perkantoran")
    @Expose
    private var perkantoran: Int? = null
    @SerializedName("sarana_olah_raga")
    @Expose
    private var saranaOlahRaga: Int? = null
    @SerializedName("tempat_ibadah")
    @Expose
    private var tempatIbadah: Int? = null
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

    fun getJaminanTanahBangunanId(): Int? {
        return jaminanTanahBangunanId
    }

    fun setJaminanTanahBangunanId(jaminanTanahBangunanId: Int?) {
        this.jaminanTanahBangunanId = jaminanTanahBangunanId
    }

    fun getAngkutanUmum(): Int? {
        return angkutanUmum
    }

    fun setAngkutanUmum(angkutanUmum: Int?) {
        this.angkutanUmum = angkutanUmum
    }

    fun getPasar(): Int? {
        return pasar
    }

    fun setPasar(pasar: Int?) {
        this.pasar = pasar
    }

    fun getSekolah(): Int? {
        return sekolah
    }

    fun setSekolah(sekolah: Int?) {
        this.sekolah = sekolah
    }

    fun getRumahSakitPuskesmas(): Int? {
        return rumahSakitPuskesmas
    }

    fun setRumahSakitPuskesmas(rumahSakitPuskesmas: Int?) {
        this.rumahSakitPuskesmas = rumahSakitPuskesmas
    }

    fun getHiburan(): Int? {
        return hiburan
    }

    fun setHiburan(hiburan: Int?) {
        this.hiburan = hiburan
    }

    fun getPerkantoran(): Int? {
        return perkantoran
    }

    fun setPerkantoran(perkantoran: Int?) {
        this.perkantoran = perkantoran
    }

    fun getSaranaOlahRaga(): Int? {
        return saranaOlahRaga
    }

    fun setSaranaOlahRaga(saranaOlahRaga: Int?) {
        this.saranaOlahRaga = saranaOlahRaga
    }

    fun getTempatIbadah(): Int? {
        return tempatIbadah
    }

    fun setTempatIbadah(tempatIbadah: Int?) {
        this.tempatIbadah = tempatIbadah
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