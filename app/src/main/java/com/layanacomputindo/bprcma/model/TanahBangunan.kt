package com.layanacomputindo.bprcma.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class TanahBangunan {
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("hak_atas_tanah")
    @Expose
    private var hakAtasTanah: String? = null
    @SerializedName("nomor_hak")
    @Expose
    private var nomorHak: String? = null
    @SerializedName("no_su_tanggal_su")
    @Expose
    private var noSuTanggalSu: String? = null
    @SerializedName("atas_nama_pemegang_hak")
    @Expose
    private var atasNamaPemegangHak: String? = null
    @SerializedName("tanggal_berakhir_hak")
    @Expose
    private var tanggalBerakhirHak: String? = null
    @SerializedName("no_surat_izin_membangun")
    @Expose
    private var noSuratIzinMembangun: String? = null
    @SerializedName("no_nib_tanah")
    @Expose
    private var noNibTanah: String? = null
    @SerializedName("asal_hak_atas_tanah")
    @Expose
    private var asalHakAtasTanah: String? = null
    @SerializedName("riwayat_singkat_tanah")
    @Expose
    private var riwayatSingkatTanah: String? = null
    @SerializedName("bentuk_tanah")
    @Expose
    private var bentukTanah: String? = null
    @SerializedName("luas_tanah")
    @Expose
    private var luasTanah: String? = null
    @SerializedName("lebar_tanah")
    @Expose
    private var lebarTanah: String? = null
    @SerializedName("panjang_tanah")
    @Expose
    private var panjangTanah: String? = null
    @SerializedName("permukaan_tanah_dari_jalan")
    @Expose
    private var permukaanTanahDariJalan: String? = null
    @SerializedName("peruntukan")
    @Expose
    private var peruntukan: String? = null
    @SerializedName("jenis_tanah")
    @Expose
    private var jenisTanah: String? = null
    @SerializedName("jalan_utama")
    @Expose
    private var jalanUtama: String? = null
    @SerializedName("lebar_jalan_utama")
    @Expose
    private var lebarJalanUtama: String? = null
    @SerializedName("jalan_penghubung")
    @Expose
    private var jalanPenghubung: String? = null
    @SerializedName("lebar_jalan_penghubung")
    @Expose
    private var lebarJalanPenghubung: String? = null
    @SerializedName("keterangan_jalan")
    @Expose
    private var keteranganJalan: String? = null
    @SerializedName("tahun_dibangun")
    @Expose
    private var tahunDibangun: Int? = null
    @SerializedName("luas_bangunan")
    @Expose
    private var luasBangunan: String? = null
    @SerializedName("jenis_bangunan")
    @Expose
    private var jenisBangunan: String? = null
    @SerializedName("konstruksi")
    @Expose
    private var konstruksi: String? = null
    @SerializedName("kualitas_bangunan")
    @Expose
    private var kualitasBangunan: String? = null
    @SerializedName("denah_lokasi")
    @Expose
    private var denahLokasi: String? = null
    @SerializedName("latitude")
    @Expose
    private var latitude: String? = null
    @SerializedName("longitude")
    @Expose
    private var longitude: String? = null
    @SerializedName("created_at")
    @Expose
    private var createdAt: String? = null
    @SerializedName("updated_at")
    @Expose
    private var updatedAt: String? = null
    @SerializedName("kredit_id")
    @Expose
    private var kreditId: Int? = null

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getHakAtasTanah(): String? {
        return hakAtasTanah
    }

    fun setHakAtasTanah(hakAtasTanah: String) {
        this.hakAtasTanah = hakAtasTanah
    }

    fun getNomorHak(): String? {
        return nomorHak
    }

    fun setNomorHak(nomorHak: String) {
        this.nomorHak = nomorHak
    }

    fun getNoSuTanggalSu(): String? {
        return noSuTanggalSu
    }

    fun setNoSuTanggalSu(noSuTanggalSu: String) {
        this.noSuTanggalSu = noSuTanggalSu
    }

    fun getAtasNamaPemegangHak(): String? {
        return atasNamaPemegangHak
    }

    fun setAtasNamaPemegangHak(atasNamaPemegangHak: String) {
        this.atasNamaPemegangHak = atasNamaPemegangHak
    }

    fun getTanggalBerakhirHak(): String? {
        return tanggalBerakhirHak
    }

    fun setTanggalBerakhirHak(tanggalBerakhirHak: String) {
        this.tanggalBerakhirHak = tanggalBerakhirHak
    }

    fun getNoSuratIzinMembangun(): String? {
        return noSuratIzinMembangun
    }

    fun setNoSuratIzinMembangun(noSuratIzinMembangun: String) {
        this.noSuratIzinMembangun = noSuratIzinMembangun
    }

    fun getNoNibTanah(): String? {
        return noNibTanah
    }

    fun setNoNibTanah(noNibTanah: String) {
        this.noNibTanah = noNibTanah
    }

    fun getAsalHakAtasTanah(): String? {
        return asalHakAtasTanah
    }

    fun setAsalHakAtasTanah(asalHakAtasTanah: String) {
        this.asalHakAtasTanah = asalHakAtasTanah
    }

    fun getRiwayatSingkatTanah(): String? {
        return riwayatSingkatTanah
    }

    fun setRiwayatSingkatTanah(riwayatSingkatTanah: String) {
        this.riwayatSingkatTanah = riwayatSingkatTanah
    }

    fun getBentukTanah(): String? {
        return bentukTanah
    }

    fun setBentukTanah(bentukTanah: String) {
        this.bentukTanah = bentukTanah
    }

    fun getLuasTanah(): String? {
        return luasTanah
    }

    fun setLuasTanah(luasTanah: String) {
        this.luasTanah = luasTanah
    }

    fun getLebarTanah(): String? {
        return lebarTanah
    }

    fun setLebarTanah(lebarTanah: String) {
        this.lebarTanah = lebarTanah
    }

    fun getPanjangTanah(): String? {
        return panjangTanah
    }

    fun setPanjangTanah(panjangTanah: String) {
        this.panjangTanah = panjangTanah
    }

    fun getPermukaanTanahDariJalan(): String? {
        return permukaanTanahDariJalan
    }

    fun setPermukaanTanahDariJalan(permukaanTanahDariJalan: String) {
        this.permukaanTanahDariJalan = permukaanTanahDariJalan
    }

    fun getPeruntukan(): String? {
        return peruntukan
    }

    fun setPeruntukan(peruntukan: String) {
        this.peruntukan = peruntukan
    }

    fun getJenisTanah(): String? {
        return jenisTanah
    }

    fun setJenisTanah(jenisTanah: String) {
        this.jenisTanah = jenisTanah
    }

    fun getJalanUtama(): String? {
        return jalanUtama
    }

    fun setJalanUtama(jalanUtama: String) {
        this.jalanUtama = jalanUtama
    }

    fun getLebarJalanUtama(): String? {
        return lebarJalanUtama
    }

    fun setLebarJalanUtama(lebarJalanUtama: String) {
        this.lebarJalanUtama = lebarJalanUtama
    }

    fun getJalanPenghubung(): String? {
        return jalanPenghubung
    }

    fun setJalanPenghubung(jalanPenghubung: String) {
        this.jalanPenghubung = jalanPenghubung
    }

    fun getLebarJalanPenghubung(): String? {
        return lebarJalanPenghubung
    }

    fun setLebarJalanPenghubung(lebarJalanPenghubung: String) {
        this.lebarJalanPenghubung = lebarJalanPenghubung
    }

    fun getKeteranganJalan(): String? {
        return keteranganJalan
    }

    fun setKeteranganJalan(keteranganJalan: String) {
        this.keteranganJalan = keteranganJalan
    }

    fun getTahunDibangun(): Int? {
        return tahunDibangun
    }

    fun setTahunDibangun(tahunDibangun: Int?) {
        this.tahunDibangun = tahunDibangun
    }

    fun getLuasBangunan(): String? {
        return luasBangunan
    }

    fun setLuasBangunan(luasBangunan: String) {
        this.luasBangunan = luasBangunan
    }

    fun getJenisBangunan(): String? {
        return jenisBangunan
    }

    fun setJenisBangunan(jenisBangunan: String) {
        this.jenisBangunan = jenisBangunan
    }

    fun getKonstruksi(): String? {
        return konstruksi
    }

    fun setKonstruksi(konstruksi: String) {
        this.konstruksi = konstruksi
    }

    fun getKualitasBangunan(): String? {
        return kualitasBangunan
    }

    fun setKualitasBangunan(kualitasBangunan: String) {
        this.kualitasBangunan = kualitasBangunan
    }

    fun getDenahLokasi(): String? {
        return denahLokasi
    }

    fun setDenahLokasi(denahLokasi: String) {
        this.denahLokasi = denahLokasi
    }

    fun getLatitude(): String? {
        return latitude
    }

    fun setLatitude(latitude: String) {
        this.latitude = latitude
    }

    fun getLongitude(): String? {
        return longitude
    }

    fun setLongitude(longitude: String) {
        this.longitude = longitude
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

}