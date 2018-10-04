package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class PenilaianKesimpulan {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("jaminan_tanah_bangunan_id")
    @Expose
    private var jaminanTanahBangunanId: Int? = null
    @SerializedName("luas_tanah")
    @Expose
    private var luasTanah: String? = null
    @SerializedName("nilai_tanah")
    @Expose
    private var nilaiTanah: Int? = null
    @SerializedName("nilai_pasar_tanah")
    @Expose
    private var nilaiPasarTanah: Int? = null
    @SerializedName("luas_bangunan_sesuai_imb")
    @Expose
    private var luasBangunanSesuaiImb: String? = null
    @SerializedName("luas_bangunan_sesuai_fisik")
    @Expose
    private var luasBangunanSesuaiFisik: String? = null
    @SerializedName("nilai_bangunan")
    @Expose
    private var nilaiBangunan: Int? = null
    @SerializedName("penyusutan_bangunan")
    @Expose
    private var penyusutanBangunan: String? = null
    @SerializedName("nilai_pasar_bangunan_imb")
    @Expose
    private var nilaiPasarBangunanImb: Int? = null
    @SerializedName("nilai_pasar_bangunan_fisik")
    @Expose
    private var nilaiPasarBangunanFisik: Int? = null
    @SerializedName("nilai_tanah_bangunan_imb")
    @Expose
    private var nilaiTanahBangunanImb: Int? = null
    @SerializedName("nilai_tanah_bangunan_fisik")
    @Expose
    private var nilaiTanahBangunanFisik: Int? = null
    @SerializedName("kesimpulan")
    @Expose
    private var kesimpulan: String? = null
    @SerializedName("faktor_positif")
    @Expose
    private var faktorPositif: String? = null
    @SerializedName("faktor_negatif")
    @Expose
    private var faktorNegatif: String? = null
    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null
    @SerializedName("persen_likuidasi_fisik")
    @Expose
    private var persenLikuidasiFisik: String? = null
    @SerializedName("nilai_likuidasi_fisik")
    @Expose
    private var nilaiLikuidasiFisik: Int? = null
    @SerializedName("persen_likuidasi_imb")
    @Expose
    private var persenLikuidasiImb: String? = null
    @SerializedName("nilai_likuidasi_imb")
    @Expose
    private var nilaiLikuidasiImb: Int? = null

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

    fun getLuasTanah(): String? {
        return luasTanah
    }

    fun setLuasTanah(luasTanah: String) {
        this.luasTanah = luasTanah
    }

    fun getNilaiTanah(): Int? {
        return nilaiTanah
    }

    fun setNilaiTanah(nilaiTanah: Int?) {
        this.nilaiTanah = nilaiTanah
    }

    fun getNilaiPasarTanah(): Int? {
        return nilaiPasarTanah
    }

    fun setNilaiPasarTanah(nilaiPasarTanah: Int?) {
        this.nilaiPasarTanah = nilaiPasarTanah
    }

    fun getLuasBangunanSesuaiImb(): String? {
        return luasBangunanSesuaiImb
    }

    fun setLuasBangunanSesuaiImb(luasBangunanSesuaiImb: String) {
        this.luasBangunanSesuaiImb = luasBangunanSesuaiImb
    }

    fun getLuasBangunanSesuaiFisik(): String? {
        return luasBangunanSesuaiFisik
    }

    fun setLuasBangunanSesuaiFisik(luasBangunanSesuaiFisik: String) {
        this.luasBangunanSesuaiFisik = luasBangunanSesuaiFisik
    }

    fun getNilaiBangunan(): Int? {
        return nilaiBangunan
    }

    fun setNilaiBangunan(nilaiBangunan: Int?) {
        this.nilaiBangunan = nilaiBangunan
    }

    fun getPenyusutanBangunan(): String? {
        return penyusutanBangunan
    }

    fun setPenyusutanBangunan(penyusutanBangunan: String) {
        this.penyusutanBangunan = penyusutanBangunan
    }

    fun getNilaiPasarBangunanImb(): Int? {
        return nilaiPasarBangunanImb
    }

    fun setNilaiPasarBangunanImb(nilaiPasarBangunanImb: Int?) {
        this.nilaiPasarBangunanImb = nilaiPasarBangunanImb
    }

    fun getNilaiPasarBangunanFisik(): Int? {
        return nilaiPasarBangunanFisik
    }

    fun setNilaiPasarBangunanFisik(nilaiPasarBangunanFisik: Int?) {
        this.nilaiPasarBangunanFisik = nilaiPasarBangunanFisik
    }

    fun getNilaiTanahBangunanImb(): Int? {
        return nilaiTanahBangunanImb
    }

    fun setNilaiTanahBangunanImb(nilaiTanahBangunanImb: Int?) {
        this.nilaiTanahBangunanImb = nilaiTanahBangunanImb
    }

    fun getNilaiTanahBangunanFisik(): Int? {
        return nilaiTanahBangunanFisik
    }

    fun setNilaiTanahBangunanFisik(nilaiTanahBangunanFisik: Int?) {
        this.nilaiTanahBangunanFisik = nilaiTanahBangunanFisik
    }

    fun getKesimpulan(): String? {
        return kesimpulan
    }

    fun setKesimpulan(kesimpulan: String) {
        this.kesimpulan = kesimpulan
    }

    fun getFaktorPositif(): String? {
        return faktorPositif
    }

    fun setFaktorPositif(faktorPositif: String) {
        this.faktorPositif = faktorPositif
    }

    fun getFaktorNegatif(): String? {
        return faktorNegatif
    }

    fun setFaktorNegatif(faktorNegatif: String) {
        this.faktorNegatif = faktorNegatif
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

    fun getPersenLikuidasiFisik(): String? {
        return persenLikuidasiFisik
    }

    fun setPersenLikuidasiFisik(persenLikuidasiFisik: String) {
        this.persenLikuidasiFisik = persenLikuidasiFisik
    }

    fun getNilaiLikuidasiFisik(): Int? {
        return nilaiLikuidasiFisik
    }

    fun setNilaiLikuidasiFisik(nilaiLikuidasiFisik: Int?) {
        this.nilaiLikuidasiFisik = nilaiLikuidasiFisik
    }

    fun getPersenLikuidasiImb(): String? {
        return persenLikuidasiImb
    }

    fun setPersenLikuidasiImb(persenLikuidasiImb: String) {
        this.persenLikuidasiImb = persenLikuidasiImb
    }

    fun getNilaiLikuidasiImb(): Int? {
        return nilaiLikuidasiImb
    }

    fun setNilaiLikuidasiImb(nilaiLikuidasiImb: Int?) {
        this.nilaiLikuidasiImb = nilaiLikuidasiImb
    }
}