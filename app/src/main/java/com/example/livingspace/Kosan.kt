package com.example.livingspace

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kosan(
    val id: Int,
    val name: String,
    val location: String,
    val price: Int,
    val rating: Float,
    val reviews: Int,
    val distance: String,
    val imageUrl: String,
    val description: String,
    val facilities: List<String>,
    val type: String,
    val available: Int,
    val owner: String,
    val ownerPhone: String,
    var isFavorite: Boolean = false
) : Parcelable {


    companion object {
        fun getKosanList(): List<Kosan> {
            return listOf(
                Kosan(
                    id = 1,
                    name = "Kosan Melati Premium",
                    location = "Sleman, Yogyakarta",
                    price = 1500000,
                    rating = 4.8f,
                    reviews = 124,
                    distance = "0.5 km",
                    imageUrl = "https://images.unsplash.com/photo-1638454668466-e8dbd5462f20?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxsdXh1cnklMjBhcGFydG1lbnQlMjBiZWRyb29tfGVufDF8fHx8MTc2NTI5NTk5Nnww&ixlib=rb-4.1.0&q=80&w=1080",
                    description = "Kosan premium dengan fasilitas lengkap, dekat dengan kampus dan pusat perbelanjaan. Lingkungan aman dan nyaman.",
                    facilities = listOf("WiFi", "AC", "Kasur", "Lemari", "Kamar Mandi Dalam", "Dapur", "Parkir"),
                    type = "Campur",
                    available = 3,
                    owner = "Ibu Siti Rahayu",
                    ownerPhone = "+62 831-3731-0583"
                ),
                Kosan(
                    id = 2,
                    name = "Kosan Mawar Residence",
                    location = "Depok, Sleman",
                    price = 1800000,
                    rating = 4.9f,
                    reviews = 98,
                    distance = "1.2 km",
                    imageUrl = "https://images.unsplash.com/photo-1758413350815-7b06dbbfb9a7?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxtb2Rlcm4lMjBzdHVkZW50JTIwcm9vbXxlbnwxfHx8fDE3NjUzODA1MDF8MA&ixlib=rb-4.1.0&q=80&w=1080",
                    description = "Residence eksklusif untuk mahasiswa dan profesional muda. Dilengkapi dengan gym dan coworking space.",
                    facilities = listOf("WiFi", "AC", "Kasur", "Meja Belajar", "Gym", "Laundry", "CCTV", "Security 24 Jam"),
                    type = "Campur",
                    available = 2,
                    owner = "Bapak Andi Wijaya",
                    ownerPhone = "+62 831-3731-0583"
                ),
                Kosan(
                    id = 3,
                    name = "Kosan Anggrek",
                    location = "Caturtunggal, Depok",
                    price = 1200000,
                    rating = 4.6f,
                    reviews = 87,
                    distance = "0.3 km",
                    imageUrl = "https://images.unsplash.com/photo-1594130139005-3f0c0f0e7c5e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxjb3p5JTIwYm9hcmRpbmclMjBob3VzZXxlbnwxfHx8fDE3NjUzODA1MDF8MA&ixlib=rb-4.1.0&q=80&w=1080",
                    description = "Kosan nyaman dengan harga terjangkau. Cocok untuk mahasiswa yang mencari tempat dekat kampus.",
                    facilities = listOf("WiFi", "Kasur", "Lemari", "Kamar Mandi Luar", "Dapur", "Jemuran"),
                    type = "Putri",
                    available = 5,
                    owner = "Ibu Dewi Lestari",
                    ownerPhone = "+62 831-3731-0583"
                ),
                Kosan(
                    id = 4,
                    name = "Kosan Sakura House",
                    location = "Condong Catur",
                    price = 1350000,
                    rating = 4.7f,
                    reviews = 102,
                    distance = "0.8 km",
                    imageUrl = "https://images.unsplash.com/photo-1613575831056-0acd5da8f085?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxhcGFydG1lbnQlMjBsaXZpbmclMjByb29tfGVufDF8fHx8MTc2NTMwMzQyMXww&ixlib=rb-4.1.0&q=80&w=1080",
                    description = "Kosan modern dengan interior minimalis Jepang. Suasana tenang dan nyaman untuk belajar.",
                    facilities = listOf("WiFi", "AC", "Kasur", "Meja Belajar", "Kamar Mandi Dalam", "Ruang Tamu", "Parkir"),
                    type = "Putra",
                    available = 4,
                    owner = "Bapak Hendra Kusuma",
                    ownerPhone = "+62 831-3731-0583"
                ),
                Kosan(
                    id = 5,
                    name = "Kosan Dahlia Residence",
                    location = "Terban, Gondokusuman",
                    price = 1650000,
                    rating = 4.8f,
                    reviews = 115,
                    distance = "1.5 km",
                    imageUrl = "https://images.unsplash.com/photo-1710046385385-a37df445b619?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxtb2Rlcm4lMjBob3N0ZWx8ZW58MXx8fHwxNzY1MzQwNTEyfDA&ixlib=rb-4.1.0&q=80&w=1080",
                    description = "Kosan strategis dekat dengan berbagai kampus dan pusat kota. Fasilitas lengkap dan modern.",
                    facilities = listOf("WiFi", "AC", "Kasur", "Lemari", "Kamar Mandi Dalam", "Smart TV", "Kulkas", "Security"),
                    type = "Putri",
                    available = 3,
                    owner = "Ibu Linda Permata",
                    ownerPhone = "+62 831-3731-0583"
                ),
                Kosan(
                    id = 6,
                    name = "Kosan Kenanga",
                    location = "Tegalrejo, Yogyakarta",
                    price = 950000,
                    rating = 4.5f,
                    reviews = 64,
                    distance = "2.1 km",
                    imageUrl = "https://images.unsplash.com/photo-1709056330726-00a8ea31059a?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxtb2Rlcm4lMjBib2FyZGluZyUyMGhvdXNlJTIwZXh0ZXJpb3J8ZW58MXx8fHwxNzY1Mzc4Mzk0fDA&ixlib=rb-4.1.0&q=80&w=1080",
                    description = "Kosan hemat dengan fasilitas standar. Cocok untuk mahasiswa dengan budget terbatas.",
                    facilities = listOf("WiFi", "Kasur", "Lemari", "Kamar Mandi Luar", "Dapur"),
                    type = "Campur",
                    available = 6,
                    owner = "Bapak Surya Atmaja",
                    ownerPhone = "+62 831-3731-0583"
                ),
                Kosan(
                    id = 7,
                    name = "Kosan Tulip Exclusive",
                    location = "Seturan, Depok",
                    price = 2000000,
                    rating = 4.9f,
                    reviews = 156,
                    distance = "0.9 km",
                    imageUrl = "https://images.unsplash.com/photo-1611095459865-47682ae3c41c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxjb3p5JTIwYmVkcm9vbSUyMGludGVyaW9yfGVufDF8fHx8MTc2NTMwODU2OHww&ixlib=rb-4.1.0&q=80&w=1080",
                    description = "Kosan mewah dengan fasilitas bintang lima. Rooftop garden, gym, dan coworking space tersedia.",
                    facilities = listOf("WiFi", "AC", "Kasur King", "Smart TV", "Kulkas", "Gym", "Rooftop", "Elevator", "Housekeeping"),
                    type = "Campur",
                    available = 2,
                    owner = "PT. Living Premium",
                    ownerPhone = "+62 831-3731-0583"
                ),
                Kosan(
                    id = 8,
                    name = "Kosan Flamboyan",
                    location = "Kolombo, Sleman",
                    price = 1100000,
                    rating = 4.4f,
                    reviews = 73,
                    distance = "1.8 km",
                    imageUrl = "https://images.unsplash.com/photo-1515263487990-61b07816b324?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxzdHVkZW50JTIwYXBhcnRtZW50JTIwcm9vbXxlbnwxfHx8fDE3NjUzNzgzOTZ8MA&ixlib=rb-4.1.0&q=80&w=1080",
                    description = "Kosan dengan suasana kekeluargaan. Area parkir luas dan taman yang asri.",
                    facilities = listOf("WiFi", "Kipas Angin", "Kasur", "Lemari", "Kamar Mandi Luar", "Taman", "Parkir"),
                    type = "Putra",
                    available = 7,
                    owner = "Ibu Ratna Sari",
                    ownerPhone = "+62 831-3731-0583"
                )
            )
        }
    }
}