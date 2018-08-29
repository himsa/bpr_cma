package com.layanacomputindo.bprcma.rest

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.layanacomputindo.bprcma.BuildConfig
import com.layanacomputindo.bprcma.model.*
import com.layanacomputindo.bprcma.util.Config
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.nio.charset.Charset

class RestClient {
    companion object {
        private var toast: Toast? = null
        private var mContext: Context? = null
        private var gitApiInterface: GitApiInterface? = null
        private val baseUrl = BuildConfig.BASE_URL_API
        private var sharedPref: SharedPreferences? = null
        private val httpClient = OkHttpClient.Builder()
        private val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create()
        private val builder = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))

        fun retrofit(): Retrofit {
            val client = httpClient.build()
            return builder.client(client).build()
        }

        fun getClient(): GitApiInterface? {
            if (gitApiInterface == null) {

                val gson = GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create()

                val client = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
                gitApiInterface = client.create(GitApiInterface::class.java)
            }
            return gitApiInterface
        }

        fun getClient(context: Context?): GitApiInterface {
            if (context != null) {
                mContext = context
                sharedPref = mContext!!.getSharedPreferences(Config.PREF_NAME, Activity.MODE_PRIVATE)
                httpClient.interceptors().add(contentType)
                httpClient.interceptors().add(authentication)
                httpClient.interceptors().add(logging)
            }
            val client = httpClient.build()
            val retrofit = builder.client(client).build()
            return retrofit.create(GitApiInterface::class.java)
        }

        private val contentType = Interceptor { chain ->
            val originalRequest = chain.request()
            val authenticationRequest = originalRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .build()

            chain.proceed(authenticationRequest)
        }

        private val authentication = Interceptor { chain ->
            val originalRequest = chain.request()
            val authenticationRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + sharedPref!!.getString(Config.USER_TOKEN, Config.EMPTY)!!)
                    .build()

            chain.proceed(authenticationRequest)
        }

        private val logging = Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            val responseBody = response.body()
            val source = responseBody.source()
            source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()
            val responseBodyString = buffer.clone().readString(Charset.forName("UTF-8"))

            if (response.code() != 200) {
                Handler(Looper.getMainLooper()).post {
                    try {
                        Log.d("onFailure Body", responseBodyString)
                        val error = gson.fromJson(responseBodyString, Error::class.java)
                        if (error.message.equals("token_expired", ignoreCase = true)) {
                            showAToast("Sesi Anda telah habis atau Anda tidak memiliki akses pada menu ini. Silahkan logout terlebih dahulu")
                        }
                        //showAToast(error.getMessage());
                    } catch (e: Exception) {

                    }
                }
            }

            response
        }

        private fun showAToast(st: String) { //"Toast toast" is declared in the class
            try {
                toast!!.view.isShown     // true if visible
                toast!!.setText(st)
            } catch (e: Exception) {         // invisible if exception
                toast = Toast.makeText(mContext, st, Toast.LENGTH_LONG)
            }

            toast!!.show()  //finally display it
        }
    }

    interface GitApiInterface {
        @FormUrlEncoded
        @POST("api/login")
        fun getToken(@Field("email") email: String,
                     @Field("password") password: String): Call<Result<Token>>


        //form
        @FormUrlEncoded
        @POST("api/debitur")
        fun getNewUser(@Field("user_id") userId: Int): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/debitur/{debitur_id}")
        fun sendNewDebitur(@Path("debitur_id") debiturId: Int,
                           @Field("nama") nama: String,
                           @Field("tempat_lahir") tempat_lahir: String,
                           @Field("tanggal_lahir") tanggal_lahir: String,
                           @Field("no_ktp") no_ktp: String,
                           @Field("alamat") alamat: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/debitur/telepon/{debitur_id}")
        fun sendTelpDebitur(@Path("debitur_id") debiturId: Int,
                            @Field("no_telepon") no_telepon: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/debitur/foto/{debitur_id}")
        fun sendFotoDebitur(@Path("debitur_id") debiturId: Int,
                            @Field("foto_ktp") foto_ktp: String,
                            @Field("foto_dengan_ktp") foto_dengan_ktp: String,
                            @Field("foto_kartu_keluarga") foto_kartu_keluarga: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/debitur/pasangan/{debitur_id}")
        fun sendPasanganDebitur(@Path("debitur_id") debiturId: Int,
                                @Field("nama") nama: String,
                                @Field("tempat_lahir") tempat_lahir: String,
                                @Field("tanggal_lahir") tanggal_lahir: String,
                                @Field("no_ktp") no_ktp: String,
                                @Field("alamat") alamat: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/debitur/pasangan/telepon/{debitur_id}")
        fun sendTelpPasanganDebitur(@Path("debitur_id") debiturId: Int,
                                    @Field("no_telepon") no_telepon: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/debitur/pasangan/foto/{debitur_id}")
        fun sendFotoPasanganDebitur(@Path("debitur_id") debiturId: Int,
                                    @Field("foto") foto: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/debitur/tempat_tinggal/{debitur_id}")
        fun sendTempatTinggalDebitur(@Path("debitur_id") debiturId: Int,
                                     @Field("latitude") latitude: String,
                                     @Field("longitude") longitude: String,
                                     @Field("status") status: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/debitur/tempat_tinggal/foto/{debitur_id}")
        fun sendFotoTempatTinggalDebitur(@Path("debitur_id") debiturId: Int,
                                         @Field("foto_dokumen") fotoDokumen: String,
                                         @Field("foto_1") foto_1: String,
                                         @Field("foto_2") foto_2: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/debitur/pekerjaan/{debitur_id}")
        fun sendPekerjaanDebitur(@Path("debitur_id") debiturId: Int,
                                     @Field("latitude") latitude: String,
                                     @Field("longitude") longitude: String,
                                     @Field("jenis_utama_pekerjaan") jenis_utama_pekerjaan: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/debitur/tempat_tinggal/foto/{debitur_id}")
        fun sendFotoPekerjaanDebitur(@Path("debitur_id") debiturId: Int,
                                         @Field("foto_1") foto_1: String,
                                         @Field("foto_2") foto_2: String,
                                         @Field("foto_pendukung_1") foto_pendukung_1: String,
                                         @Field("foto_pendukung_2") foto_pendukung_2: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/debitur/kredit/new/{debitur_id}")
        fun sendPermohonanKredit(@Path("debitur_id") debiturId: Int,
                                 @Field("jangka_waktu") jangka_waktu: String,
                                 @Field("tujuan_kredit") tujuan_kredit: String,
                                 @Field("nominal") nominal: Int,
                                 @Field("sektor_ekonomi") sektor_ekonomi: String,
                                 @Field("spesifikasi_usaha") spesifikasi_usaha: String,
                                 @Field("detail_tujuan_kredit") detail_tujuan_kredit: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/update/{kredit_id}")
        fun updatePermohonanKredit(@Path("kredit_id") kredit_id: Int,
                                 @Field("jangka_waktu") jangka_waktu: String,
                                 @Field("tujuan_kredit") tujuan_kredit: String,
                                 @Field("nominal") nominal: Int,
                                 @Field("sektor_ekonomi") sektor_ekonomi: String,
                                 @Field("spesifikasi_usaha") spesifikasi_usaha: String,
                                 @Field("detail_tujuan_kredit") detail_tujuan_kredit: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/approve/{kredit_id}")
        fun sendAproval(@Path("kredit_id") kredit_id: Int,
                                   @Field("status_kredit") status_kredit: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/usaha/{kredit_id}")
        fun sendUsahaDebitur(@Path("kredit_id") kredit_id: Int,
                                 @Field("keterangan") keterangan: String,
                                 @Field("detail_keterangan") detail_keterangan: String,
                                 @Field("mulai_sejak") mulai_sejak: String,
                                 @Field("status") status: String,
                                 @Field("detail_deskripsi_usaha") detail_deskripsi_usaha: String,
                                 @Field("deskripsi_penghasilan_sampingan") deskripsi_penghasilan_sampingan: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/usaha/pasangan/{kredit_id}")
        fun sendUsahaPasanganDebitur(@Path("kredit_id") kredit_id: Int,
                             @Field("deskripsi") deskripsi: String,
                             @Field("detail_keterangan") detail_keterangan: String,
                             @Field("mulai_sejak") mulai_sejak: String,
                             @Field("status") status: String,
                             @Field("detail_deskripsi_usaha") detail_deskripsi_usaha: String,
                             @Field("deskripsi_penghasilan_sampingan") deskripsi_penghasilan_sampingan: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/pengembalian_kredit/{kredit_id}")
        fun sendPengembalianKredit(@Path("kredit_id") kredit_id: Int,
                                     @Field("sumber_pengembalian") sumber_pengembalian: String,
                                     @Field("detail_sumber_pengembalian") detail_sumber_pengembalian: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/keuangan/{kredit_id}")
        fun sendKeuanganAktivaDebitur(@Path("kredit_id") kredit_id: Int,
                                   @Field("periode") periode: String,
                                   @Field("total_aktiva") total_aktiva: Int): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/keuangan/{kredit_id}")
        fun sendKeuanganPasivaDebitur(@Path("kredit_id") kredit_id: Int,
                                      @Field("total_pasiva") total_pasiva: Int): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/keuangan/aktiva_lancar/{kredit_id}")
        fun sendAktivaLancar(@Path("kredit_id") kredit_id: Int,
                                @Field("kas") kas: Int,
                                @Field("bank") bank: Int,
                                @Field("piutang") piutang: Int,
                                @Field("persediaan") persediaan: Int,
                                @Field("sub_jumlah") sub_jumlah: Int): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/keuangan/aktiva_tetap/{kredit_id}")
        fun sendAktivaTetap(@Path("kredit_id") kredit_id: Int,
                            @Field("tanah") tanah: Int,
                            @Field("bangunan") bangunan: Int,
                            @Field("kendaraan") kendaraan: Int,
                            @Field("inventaris_lain") inventaris_lain: Int,
                            @Field("akumulasi_depresiasi") akumulasi_depresiasi: Int,
                            @Field("sub_jumlah") sub_jumlah: Int): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/kredit/keuangan/pasiva_hutang/{kredit_id}")
        fun sendPasivaHutang(@Path("kredit_id") kredit_id: Int,
                             @Field("bank") bank: Int,
                             @Field("dagang") dagang: Int,
                             @Field("jangka_panjang") jangka_panjang: Int,
                             @Field("kewajiban_membayar") kewajiban_membayar: Int,
                             @Field("sub_jumlah") sub_jumlah: Int): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/keuangan/pasiva_modal/{kredit_id}")
        fun sendPasivaModal(@Path("kredit_id") kredit_id: Int,
                             @Field("modal") modal: Int,
                             @Field("modal_kerja") modal_kerja: Int,
                             @Field("cadangan_laba_ditahan") cadangan_laba_ditahan: Int,
                             @Field("laba_tahun_berjalan") laba_tahun_berjalan: Int,
                             @Field("sub_jumlah") sub_jumlah: Int): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/keuangan/performa/{kredit_id}")
        fun sendPerforma(@Path("kredit_id") kredit_id: Int,
                            @Field("omzet") omzet: Int,
                            @Field("keterangan_omzet") keterangan_omzet: String,
                            @Field("hpp") hpp: Int,
                            @Field("gaji_karyawan") gaji_karyawan: Int,
                            @Field("sewa_tempat_usaha") sewa_tempat_usaha: Int,
                            @Field("listrik_air_telepon_usaha") listrik_air_telepon_usaha: Int,
                            @Field("admin_keamanan_lingkungan") admin_keamanan_lingkungan: Int,
                            @Field("biaya_lain") biaya_lain: Int,
                            @Field("jumlah_biaya_usaha") jumlah_biaya_usaha: Int,
                            @Field("keterangan_biaya") keterangan_biaya: String,
                            @Field("keuntungan_usaha") keuntungan_usaha: Int,
                            @Field("gross_profit_margin") gross_profit_margin: Int,
                            @Field("pendapatan_pasangan") pendapatan_pasangan: Int,
                            @Field("pendapatan_lain") pendapatan_lain: Int,
                            @Field("keterangan_pendapatan_lain") keterangan_pendapatan_lain: String,
                            @Field("total_income_netto") total_income_netto: Int,
                            @Field("keterangan_total_income_netto") keterangan_total_income_netto: String,
                            @Field("biaya_rumah_tangga") biaya_rumah_tangga: Int,
                            @Field("listrik_air_telepon_rumah_tangga") listrik_air_telepon_rumah_tangga: Int,
                            @Field("biaya_pendidikan") biaya_pendidikan: Int,
                            @Field("biaya_sewa_lainnya") biaya_sewa_lainnya: Int,
                            @Field("total_biaya_pribadi") total_biaya_pribadi: Int,
                            @Field("keterangan_keuangan_pribadi") keterangan_keuangan_pribadi: String,
                            @Field("sisa_penghasilan_bersih") sisa_penghasilan_bersih: Int,
                            @Field("keterangan_sisa_penghasilan_bersih ") keterangan_sisa_penghasilan_bersih : String,
                            @Field("angsuran_bank_lain") angsuran_bank_lain: Int,
                            @Field("rencana_angsuran_cma") rencana_angsuran_cma: Int,
                            @Field("total_angsuran") total_angsuran: Int,
                            @Field("keterangan_angsuran") keterangan_angsuran: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/tambahan_informasi/{kredit_id}")
        fun sendTambahanInfo(@Path("kredit_id") kredit_id: Int,
                                @Field("faktor_positif") faktor_positif: String,
                                @Field("faktor_negatif") faktor_negatif: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/kesimpulan/{kredit_id}")
        fun sendKesimpulan(@Path("kredit_id") kredit_id: Int,
                                @Field("karakter") karakter: String,
                                @Field("kapasitas") kapasitas: String,
                                @Field("kapital") kapital: String,
                                @Field("jaminan") jaminan: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/usulan_kredit/{kredit_id}")
        fun sendUsulanKredit(@Path("kredit_id") kredit_id: Int,
                             @Field("kredit_id") karakter: Int,
                             @Field("jenis_kredit") jenis_kredit: String,
                             @Field("pembaharuan_plafond_kredit") pembaharuan_plafond_kredit: Int,
                             @Field("cara_pembayaran") cara_pembayaran: String,
                             @Field("suku_bunga") suku_bunga: String,
                             @Field("angsuran_per_bulan") angsuran_per_bulan: Int,
                             @Field("jangka_waktu_per_bulan") jangka_waktu_per_bulan: Int,
                             @Field("provisi_administrasi") provisi_administrasi: Int,
                             @Field("pengikatan_kredit") pengikatan_kredit: String,
                             @Field("total_kredit") total_kredit: Int,
                             @Field("pengikatan_agunan") pengikatan_agunan: String,
                             @Field("total_agunan") total_agunan: Int,
                             @Field("kesimpulan_agunan") kesimpulan_agunan: String,
                             @Field("asuransi_agunan") asuransi_agunan: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/foto_dokumen_lain/{kredit_id}")
        fun sendFotoDokumenLain(@Path("kredit_id") kredit_id: Int,
                           @Field("foto") foto: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/kredit/jaminan_kendaraan/new/{kredit_id}")
        fun sendJaminanKendaraan(@Path("kredit_id") kredit_id: Int,
                                 @Field("tanggal_pemeriksaan") tanggal_pemeriksaan: String,
                                 @Field("jenis_kendaraan") jenis_kendaraan: String,
                                 @Field("penggunaan_jaminan") penggunaan_jaminan: String,
                                 @Field("daerah_operasional_jaminan") daerah_operasional_jaminan: Int,
                                 @Field("nama_pemilik_bpkb") nama_pemilik_bpkb: String,
                                 @Field("nama_pemilik") nama_pemilik: String,
                                 @Field("hubungan_pemilik_debitur") hubungan_pemilik_debitur: String,
                                 @Field("nomor_faktur") nomor_faktur: String,
                                 @Field("nomor_mesin") nomor_mesin: String,
                                 @Field("bukti_gesek_mesin") bukti_gesek_mesin: Boolean,
                                 @Field("nomor_rangka") nomor_rangka: String,
                                 @Field("bukti_gesek_rangka") bukti_gesek_rangka: Boolean,
                                 @Field("nomor_polisi") nomor_polisi: String,
                                 @Field("nomor_bpkb") nomor_bpkb: String,
                                 @Field("warna") warna: String,
                                 @Field("tahun_pembuatan") tahun_pembuatan: Int,
                                 @Field("merk_kendaraan") merk_kendaraan: String,
                                 @Field("tipe_model_kendaraan") tipe_model_kendaraan: String,
                                 @Field("check_samsat") check_samsat: String,
                                 @Field("nama_petugas_samsat") nama_petugas_samsat: String,
                                 @Field("nomor_telepon_samsat") nomor_telepon_samsat: String,
                                 @Field("hasil_kesesuaian") hasil_kesesuaian: Boolean,
                                 @Field("keterangan_lain") keterangan_lain: String,
                                 @Field("nilai_market") nilai_market: Int,
                                 @Field("nilai_likuidasi") nilai_likuidasi: Int,
                                 @Field("nama_informan_harga") nama_informan_harga: String,
                                 @Field("pekerjaan_informan_harga") pekerjaan_informan_harga: String,
                                 @Field("telepon_informan_harga") telepon_informan_harga: String,
                                 @Field("alamat_informan_harga") alamat_informan_harga: String,
                                 @Field("pendapat_pemeriksa") pendapat_pemeriksa: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/jaminan_kendaraan/update/{jaminan_id}")
        fun updateJaminanKendaraan(@Path("jaminan_id") jaminan_id: Int,
                                 @Field("tanggal_pemeriksaan") tanggal_pemeriksaan: String,
                                 @Field("jenis_kendaraan") jenis_kendaraan: String,
                                 @Field("penggunaan_jaminan") penggunaan_jaminan: String,
                                 @Field("daerah_operasional_jaminan") daerah_operasional_jaminan: Int,
                                 @Field("nama_pemilik_bpkb") nama_pemilik_bpkb: String,
                                 @Field("nama_pemilik") nama_pemilik: String,
                                 @Field("hubungan_pemilik_debitur") hubungan_pemilik_debitur: String,
                                 @Field("nomor_faktur") nomor_faktur: String,
                                 @Field("nomor_mesin") nomor_mesin: String,
                                 @Field("bukti_gesek_mesin") bukti_gesek_mesin: Boolean,
                                 @Field("nomor_rangka") nomor_rangka: String,
                                 @Field("bukti_gesek_rangka") bukti_gesek_rangka: Boolean,
                                 @Field("nomor_polisi") nomor_polisi: String,
                                 @Field("nomor_bpkb") nomor_bpkb: String,
                                 @Field("warna") warna: String,
                                 @Field("tahun_pembuatan") tahun_pembuatan: Int,
                                 @Field("merk_kendaraan") merk_kendaraan: String,
                                 @Field("tipe_model_kendaraan") tipe_model_kendaraan: String,
                                 @Field("check_samsat") check_samsat: String,
                                 @Field("nama_petugas_samsat") nama_petugas_samsat: String,
                                 @Field("nomor_telepon_samsat") nomor_telepon_samsat: String,
                                 @Field("hasil_kesesuaian") hasil_kesesuaian: Boolean,
                                 @Field("keterangan_lain") keterangan_lain: String,
                                 @Field("nilai_market") nilai_market: Int,
                                 @Field("nilai_likuidasi") nilai_likuidasi: Int,
                                 @Field("nama_informan_harga") nama_informan_harga: String,
                                 @Field("pekerjaan_informan_harga") pekerjaan_informan_harga: String,
                                 @Field("telepon_informan_harga") telepon_informan_harga: String,
                                 @Field("alamat_informan_harga") alamat_informan_harga: String,
                                 @Field("pendapat_pemeriksa") pendapat_pemeriksa: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/jaminan_kendaraan/foto/{jaminan_id}")
        fun sendJaminanKendaraanFoto(@Path("jaminan_id") jaminan_id: Int,
                                     @Field("tampak_muka") tampak_muka: String,
                                     @Field("tampak_belakang") tampak_belakang: String,
                                     @Field("tampak_sisi_kanan") tampak_sisi_kanan: String,
                                     @Field("tampak_sisi_kiri") tampak_sisi_kiri: String,
                                     @Field("foto_nomor_rangka") foto_nomor_rangka: String,
                                     @Field("foto_nomor_mesin") foto_nomor_mesin: String,
                                     @Field("foto_bpkb") foto_bpkb: String,
                                     @Field("foto_stnk") foto_stnk: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/kredit/jaminan_tanah_bangunan/new/{kredit_id}")
        fun sendJaminanTanah(@Path("kredit_id") kredit_id: Int,
                             @Field("hak_atas_tanah") hak_atas_tanah: String,
                             @Field("nomor_hak") nomor_hak: String,
                             @Field("no_su_tanggal_su") no_su_tanggal_su: String,
                             @Field("atas_nama_pemegang_hak") atas_nama_pemegang_hak: String,
                             @Field("tanggal_berakhir_hak") tanggal_berakhir_hak: String,
                             @Field("no_surat_izin_membangun") no_surat_izin_membangun: String,
                             @Field("no_nib_tanah") no_nib_tanah: String,
                             @Field("asal_hak_atas_tanah") asal_hak_atas_tanah: String,
                             @Field("riwayat_singkat_tanah") riwayat_singkat_tanah: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/jaminan_tanah_bangunan/update/{jaminan_id}")
        fun updateJaminanTanah(@Path("jaminan_id") jaminan_id: Int,
                             @Field("hak_atas_tanah") hak_atas_tanah: String,
                             @Field("nomor_hak") nomor_hak: String,
                             @Field("no_su_tanggal_su") no_su_tanggal_su: String,
                             @Field("atas_nama_pemegang_hak") atas_nama_pemegang_hak: String,
                             @Field("tanggal_berakhir_hak") tanggal_berakhir_hak: String,
                             @Field("no_surat_izin_membangun") no_surat_izin_membangun: String,
                             @Field("no_nib_tanah") no_nib_tanah: String,
                             @Field("asal_hak_atas_tanah") asal_hak_atas_tanah: String,
                             @Field("riwayat_singkat_tanah") riwayat_singkat_tanah: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/jaminan_tanah_bangunan/update/{jaminan_id}")
        fun sendJaminanTanah2(@Path("jaminan_id") jaminan_id: Int,
                             @Field("bentuk_tanah") bentuk_tanah: String,
                             @Field("luas_tanah") luas_tanah: Int,
                             @Field("lebar_tanah") lebar_tanah: Int,
                             @Field("panjang_tanah") panjang_tanah: Int,
                             @Field("permukaan_tanah_dari_jalan") permukaan_tanah_dari_jalan: String,
                             @Field("peruntukan") peruntukan: String,
                             @Field("jenis_tanah") jenis_tanah: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/jaminan_tanah_bangunan/update/{jaminan_id}")
        fun sendJaminanTanah3(@Path("jaminan_id") jaminan_id: Int,
                              @Field("jalan_utama") jalan_utama: String,
                              @Field("lebar_jalan_utama") lebar_jalan_utama: Int,
                              @Field("jalan_penghubung") jalan_penghubung: String,
                              @Field("lebar_jalan_penghubung") lebar_jalan_penghubung: Int,
                              @Field("keterangan_jalan") keterangan_jalan: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/jaminan_tanah_bangunan/update/{jaminan_id}")
        fun sendJaminanTanah4(@Path("jaminan_id") jaminan_id: Int,
                              @Field("tahun_dibangun") tahun_dibangun: Int,
                              @Field("luas_bangunan") luas_bangunan: Int,
                              @Field("jenis_bangunan") jenis_bangunan: String,
                              @Field("konstruksi") konstruksi: String,
                              @Field("kualitas_bangunan") kualitas_bangunan: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/jaminan_tanah_bangunan/denah/{jaminan_id}")
        fun sendDenahLokasi(@Path("jaminan_id") jaminan_id: Int,
                              @Field("denah_lokasi") denah_lokasi: String,
                              @Field("latitude") latitude: String,
                              @Field("longitude") longitude: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/jaminan_tanah_bangunan/tata_ruang/{jaminan_id}")
        fun sendTataRuang(@Path("jaminan_id") jaminan_id: Int,
                          @Field("kamar_tidur_lantai_1") kamar_tidur_lantai_1: Boolean,
                          @Field("kamar_tidur_lantai_2") kamar_tidur_lantai_2: Boolean,
                          @Field("kamar_tidur_lantai_3") kamar_tidur_lantai_3: Boolean,
                          @Field("kamar_mandi_lantai_1") kamar_mandi_lantai_1: Boolean,
                          @Field("kamar_mandi_lantai_2") kamar_mandi_lantai_2: Boolean,
                          @Field("kamar_mandi_lantai_3") kamar_mandi_lantai_3: Boolean,
                          @Field("ruang_makan_keluarga_lantai_1") ruang_makan_keluarga_lantai_1: Boolean,
                          @Field("ruang_makan_keluarga_lantai_2") ruang_makan_keluarga_lantai_2: Boolean,
                          @Field("ruang_makan_keluarga_lantai_3") ruang_makan_keluarga_lantai_3: Boolean,
                          @Field("ruang_tamu_lantai_1") ruang_tamu_lantai_1: Boolean,
                          @Field("ruang_tamu_lantai_2") ruang_tamu_lantai_2: Boolean,
                          @Field("ruang_tamu_lantai_3") ruang_tamu_lantai_3: Boolean,
                          @Field("dapur_lantai_1") dapur_lantai_1: Boolean,
                          @Field("dapur_lantai_2") dapur_lantai_2: Boolean,
                          @Field("dapur_lantai_3") dapur_lantai_3: Boolean,
                          @Field("garasi_lantai_1") garasi_lantai_1: Boolean,
                          @Field("garasi_lantai_2") garasi_lantai_2: Boolean,
                          @Field("garasi_lantai_3") garasi_lantai_3: Boolean,
                          @Field("kamar_pembantu_lantai_1") kamar_pembantu_lantai_1: Boolean,
                          @Field("kamar_pembantu_lantai_2") kamar_pembantu_lantai_2: Boolean,
                          @Field("kamar_pembantu_lantai_3") kamar_pembantu_lantai_3: Boolean): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/jaminan_tanah_bangunan/fasilitas/{jaminan_id}")
        fun sendTanahFasilitas(@Path("jaminan_id") jaminan_id: Int,
                               @Field("saluran_listrik_watt") saluran_listrik_watt: Int,
                               @Field("saluran_listrik_volt") saluran_listrik_volt: Int,
                               @Field("saluran_telepon") saluran_telepon: Boolean,
                               @Field("sumber_air") sumber_air: Boolean): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/jaminan_tanah_bangunan/fasilitas_umum/{jaminan_id}")
        fun sendTanahFasilitasUmum(@Path("jaminan_id") jaminan_id: Int,
                                   @Field("angkutan_umum") angkutan_umum: Boolean,
                                   @Field("pasar") pasar: Boolean,
                                   @Field("sekolah") sekolah: Boolean,
                                   @Field("rumah_sakit_puskesmas") rumah_sakit_puskesmas: Boolean,
                                   @Field("hiburan") hiburan: Boolean,
                                   @Field("perkantoran") perkantoran: Boolean,
                                   @Field("sarana_olah_raga") sarana_olah_raga: Boolean,
                                   @Field("tempat_ibadah") tempat_ibadah: Boolean): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/jaminan_tanah_bangunan/foto/{jaminan_id}")
        fun sendJaminanTanahFoto(@Path("jaminan_id") jaminan_id: Int,
                                     @Field("tampak_muka") tampak_muka: String,
                                     @Field("tampak_belakang") tampak_belakang: String,
                                     @Field("tampak_sisi_kanan") tampak_sisi_kanan: String,
                                     @Field("tampak_sisi_kiri") tampak_sisi_kiri: String,
                                     @Field("tampak_dalam") tampak_dalam: String,
                                     @Field("tampak_lain") tampak_lain: String,
                                     @Field("tampak_sekitar_1") tampak_sekitar_1: String,
                                     @Field("tampak_sekitar_2") tampak_sekitar_2: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/jaminan_tanah_bangunan/faktor_penilaian/{jaminan_id}")
        fun sendTanahFaktorPenilaian(@Path("jaminan_id") jaminan_id: Int,
                                     @Field("ada_jalan_akses_masuk") ada_jalan_akses_masuk: Boolean,
                                     @Field("akses_jalan_roda_4") akses_jalan_roda_4: Boolean,
                                     @Field("akses_berupa_tanah_batu") akses_berupa_tanah_batu: Boolean,
                                     @Field("dekat_sungai") dekat_sungai: Boolean,
                                     @Field("jauh_tempat_ibadah") jauh_tempat_ibadah: Boolean,
                                     @Field("dekat_lokasi_banjir") dekat_lokasi_banjir: Boolean,
                                     @Field("dekat_tegangan_listrik_tinggi") dekat_tegangan_listrik_tinggi: Boolean,
                                     @Field("tanah_perlu_diurug") tanah_perlu_diurug: Boolean,
                                     @Field("mengikuti_master_plan_pemda") mengikuti_master_plan_pemda: Boolean,
                                     @Field("lokasi_tusuk_sate") lokasi_tusuk_sate: Boolean,
                                     @Field("lokasi_tempat_ibadah") lokasi_tempat_ibadah: Boolean,
                                     @Field("lokasi_tempat_usaha") lokasi_tempat_usaha: Boolean,
                                     @Field("dekat_makam") dekat_makam: Boolean,
                                     @Field("dekat_spbu") dekat_spbu: Boolean,
                                     @Field("dekat_tpa_sampah") dekat_tpa_sampah: Boolean,
                                     @Field("dekat_sawah") dekat_sawah: Boolean,
                                     @Field("dekat_perindustrian") dekat_perindustrian: Boolean,
                                     @Field("dekat_bahaya_longsor") dekat_bahaya_longsor: Boolean,
                                     @Field("kondisi_lingkungan_prospek") kondisi_lingkungan_prospek: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/jaminan_tanah_bangunan/sumber_harga_tanah/{jaminan_id}")
        fun sendSumberHargaTanah(@Path("jaminan_id") jaminan_id: Int,
                                 @Field("kantor_pemasaran") kantor_pemasaran: Int,
                                 @Field("kantor_notaris") kantor_notaris: Int,
                                 @Field("masyarakat_sekitar") masyarakat_sekitar: Int,
                                 @Field("njop_pbb_terakhir") njop_pbb_terakhir: Int,
                                 @Field("taksiran_penilai") taksiran_penilai: Int): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/jaminan_tanah_bangunan/sumber_harga_bangunan/{jaminan_id}")
        fun sendSumberHargaBangunan(@Path("jaminan_id") jaminan_id: Int,
                                    @Field("pengembang") pengembang: Int,
                                    @Field("taksiran_penilai") taksiran_penilai: Int): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/jaminan_tanah_bangunan/penilaian_kesimpulan/{jaminan_id}")
        fun sendPenilaianKesimpulan(@Path("jaminan_id") jaminan_id: Int,
                                    @Field("luas_tanah") luas_tanah: Int,
                                    @Field("nilai_tanah") nilai_tanah: Int,
                                    @Field("nilai_pasar_tanah") nilai_pasar_tanah: Int,
                                    @Field("luas_bangunan_sesuai_imb") luas_bangunan_sesuai_imb: Int,
                                    @Field("luas_bangunan_sesuai_fisik") luas_bangunan_sesuai_fisik: Int,
                                    @Field("nilai_bangunan") nilai_bangunan: Int,
                                    @Field("penyusutan_bangunan") penyusutan_bangunan: Int,
                                    @Field("nilai_pasar_bangunan_imb") nilai_pasar_bangunan_imb: Int,
                                    @Field("nilai_pasar_bangunan_fisik") nilai_pasar_bangunan_fisik: Int,
                                    @Field("nilai_tanah_bangunan_imb") nilai_tanah_bangunan_imb: Int,
                                    @Field("nilai_tanah_bangunan_fisik") nilai_tanah_bangunan_fisik: Int,
                                    @Field("persen_likuidasi ") persen_likuidasi : Int,
                                    @Field("nilai_likuidasi") nilai_likuidasi: Int,
                                    @Field("kesimpulan") kesimpulan: String,
                                    @Field("faktor_positif") faktor_positif: String,
                                    @Field("faktor_negatif") faktor_negatif: String): Call<Result<UserId>>

        @FormUrlEncoded
        @POST("api/kredit/jaminan_tabungan_deposito/{kredit_id}")
        fun sendJaminanTabungan(@Path("kredit_id") kredit_id: Int,
                                @Field("nomor_rekening") nomor_rekening: String,
                                @Field("nomor_bilyet") nomor_bilyet: String,
                                @Field("nama") nama: String,
                                @Field("nominal") nominal: Int,
                                @Field("foto_dokumen") foto_dokumen: String): Call<Result<UserId>>
        @FormUrlEncoded
        @POST("api/jaminan_tabungan_deposito/update/{jaminan_id}")
        fun updateJaminanTabungan(@Path("jaminan_id") jaminan_id: Int,
                                @Field("nomor_rekening") nomor_rekening: String,
                                @Field("nomor_bilyet") nomor_bilyet: String,
                                @Field("nama") nama: String,
                                @Field("nominal") nominal: Int,
                                @Field("foto_dokumen") foto_dokumen: String): Call<Result<UserId>>

        //info kredit
        @FormUrlEncoded
        @POST("api/debitur/find/by_status")
        fun getInfoKreditByStatus(@Field("status") status: String): Call<Result<CurrentPage>>

        //user
        @GET("api/user")
        fun getUser(): Call<Result<User>>

        //KreditByDebitur
        @GET("api/debitur/kredit/{debitur_id}")
        fun getKreditByDebitur(@Path("debitur_id") debiturId: Int): Call<Results<Kredit>>
    }
}