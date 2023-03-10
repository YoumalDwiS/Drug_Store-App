USE [master]
GO
/****** Object:  Database [dbHalotek]    Script Date: 01/08/2022 20:26:42 ******/
CREATE DATABASE [dbHalotek]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'dbHalotek', FILENAME = N'E:\HaloTek_KEL09\HaloTek\dbHalotek.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'dbHalotek_log', FILENAME = N'E:\HaloTek_KEL09\HaloTek\dbHalotek_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [dbHalotek] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [dbHalotek].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [dbHalotek] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [dbHalotek] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [dbHalotek] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [dbHalotek] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [dbHalotek] SET ARITHABORT OFF 
GO
ALTER DATABASE [dbHalotek] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [dbHalotek] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [dbHalotek] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [dbHalotek] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [dbHalotek] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [dbHalotek] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [dbHalotek] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [dbHalotek] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [dbHalotek] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [dbHalotek] SET  DISABLE_BROKER 
GO
ALTER DATABASE [dbHalotek] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [dbHalotek] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [dbHalotek] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [dbHalotek] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [dbHalotek] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [dbHalotek] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [dbHalotek] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [dbHalotek] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [dbHalotek] SET  MULTI_USER 
GO
ALTER DATABASE [dbHalotek] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [dbHalotek] SET DB_CHAINING OFF 
GO
ALTER DATABASE [dbHalotek] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [dbHalotek] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [dbHalotek] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [dbHalotek] SET QUERY_STORE = OFF
GO
USE [dbHalotek]
GO
/****** Object:  Table [dbo].[mSupplier]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mSupplier](
	[id_supplier] [char](7) NOT NULL,
	[nama_supplier] [varchar](50) NOT NULL,
	[telp_supplier] [varchar](13) NOT NULL,
	[alamat] [varchar](100) NOT NULL,
	[status_supplier] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_supplier] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbTrPembelian]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbTrPembelian](
	[id_pembelian] [char](7) NOT NULL,
	[id_supplier] [char](7) NOT NULL,
	[tgl_pembelian] [date] NOT NULL,
	[total_harga_beli] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_pembelian] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[LaporanPembelian]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[LaporanPembelian] AS
SELECT a.id_pembelian, b.nama_supplier, a.tgl_pembelian, a.total_harga_beli
FROM     dbo.tbTrPembelian AS a INNER JOIN
                  dbo.mSupplier AS b ON a.id_supplier = b.id_supplier
GO
/****** Object:  Table [dbo].[tbTrPenjualan]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbTrPenjualan](
	[id_penjualan] [char](7) NOT NULL,
	[id_resep] [char](7) NULL,
	[tgl_penjualan] [date] NOT NULL,
	[total_harga_jual] [money] NULL,
 CONSTRAINT [PK__tbTrPenjualan] PRIMARY KEY CLUSTERED 
(
	[id_penjualan] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbDetailPenjualan]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbDetailPenjualan](
	[id_penjualan] [char](7) NOT NULL,
	[id_obat] [char](7) NOT NULL,
	[jumlah_obat] [int] NOT NULL,
	[jumlah_harga] [money] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_penjualan] ASC,
	[id_obat] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[mObat]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mObat](
	[id_obat] [char](7) NOT NULL,
	[nama_obat] [varchar](50) NOT NULL,
	[id_kategori] [char](7) NOT NULL,
	[kandungan] [varchar](100) NOT NULL,
	[jenis] [varchar](50) NOT NULL,
	[dosis] [varchar](50) NOT NULL,
	[exp_date] [date] NOT NULL,
	[stok] [int] NOT NULL,
	[harga] [money] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_obat] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[LaporanPenjualan]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[LaporanPenjualan] AS
SELECT a.id_penjualan, b.nama_obat, c.jumlah_obat, a.tgl_penjualan, c.jumlah_harga
FROM     dbo.tbTrPenjualan AS a INNER JOIN
                  dbo.tbDetailPenjualan AS c ON a.id_penjualan = c.id_penjualan INNER JOIN
                  dbo.mObat AS b ON b.id_obat = c.id_obat
GO
/****** Object:  Table [dbo].[mCustomer]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mCustomer](
	[id_customer] [char](7) NOT NULL,
	[nama_customer] [varchar](50) NOT NULL,
	[telp_customer] [varchar](13) NOT NULL,
	[status_customer] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_customer] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[mKategori]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mKategori](
	[id_kategori] [char](7) NOT NULL,
	[nama_kategori] [varchar](50) NOT NULL,
	[keterangan] [varchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_kategori] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[mResep]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mResep](
	[id_resep] [char](7) NOT NULL,
	[id_customer] [char](7) NOT NULL,
	[nama_dokter] [varchar](50) NOT NULL,
	[praktek] [varchar](50) NOT NULL,
	[kandungan] [varchar](200) NOT NULL,
	[tgl_input] [date] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_resep] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[mUser]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[mUser](
	[id_user] [char](6) NOT NULL,
	[nama_user] [varchar](50) NOT NULL,
	[username] [varchar](20) NOT NULL,
	[password] [varchar](20) NOT NULL,
	[jabatan] [varchar](20) NOT NULL,
	[telp_user] [varchar](13) NOT NULL,
	[status_user] [varchar](20) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id_user] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbDetailPembelian]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbDetailPembelian](
	[id_pembelian] [char](7) NOT NULL,
	[id_obat] [char](7) NOT NULL,
	[jumlah_obat] [int] NOT NULL,
	[jumlah_harga] [money] NULL,
PRIMARY KEY CLUSTERED 
(
	[id_pembelian] ASC,
	[id_obat] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CSR0022', N'Ardisa', N'0878098761234', N'Tidak Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0000', N'Youmal', N'0812172122', N'Tidak Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0001', N'Junaedi', N'0858755', N'Tidak Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0002', N'Ariza', N'0819212121', N'Tidak Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0003', N'Ucok Baba', N'087121212', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0004', N'Ariza Diva Rahmansyah', N'081384427623', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0005', N'Ariza', N'081923123121', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0006', N'Budi', N'081319021808', N'Tidak Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0007', N'Budi Ariyanto', N'081319021808', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0008', N'Budi', N'081212929222', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0009', N'Rian Fauzi', N'0812192129', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0010', N'Akbar', N'0812912372', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0011', N'Selamet Aziz', N'08131292732', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0012', N'Gema Ramadhan', N'0812368238', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0013', N'Tirta Indra', N'0812192272', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0014', N'Seftia', N'08129129274', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0015', N'Dito Modovan', N'08123718229', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0016', N'Arya Kusuma', N'0812928382', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0017', N'Ika Maylia', N'0812929292', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0018', N'Juan Winners', N'0829292819', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0019', N'Bang Ell', N'08129293829', N'Tidak Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0020', N'Syauqi Utomo', N'08192929292', N'Tidak Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0021', N'Sashi', N'081291929292', N'Tidak Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0022', N'Haikal Nazmi', N'0812345678901', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0023', N'Bismo Lintang Hutomo', N'089512345678', N'Tidak Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0024', N'Joko Anwar', N'081205610987', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0025', N'Joko Anwar', N'089513400986', N'Aktif')
INSERT [dbo].[mCustomer] ([id_customer], [nama_customer], [telp_customer], [status_customer]) VALUES (N'CUS0026', N'Joko Anwar Sidik', N'089509876543', N'Aktif')
GO
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0001', N'Obat Cair', N'obat ini terdiri dari zat aktif yang dilarutkan dalam cairan sehingga lebih mudah untuk diminum seka')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0002', N'Obat Bebas Terbatas', N'Obat yang dapat dibeli bebas tanpa resep dokter di toko obat berizin')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0003', N'Obat Bebas ', N'Obat yang dapat dijual bebas kepada umum tanpa resep dokter')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0004', N'Obat Keras', N'Obat yang hanya boleh diserahkan dengan resep dokter')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0005', N'Obat Wajib Apotek', N'Obat keras yang dapat diserahkan oleh apoteker di apotek tanpa resep dokter')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0006', N'Obat Golongan Narkotika', N' obat yang berasal dari tanaman atau bukan tanaman baik sintetis maupun semi sintetis yang dapat men')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0007', N'Obat Psikotropika', N'zat atau obat baik alamiah maupun sintetis bukan narkotika yang berkhasiat psikoaktif melalui pengar')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0008', N'Obat Herbal', N'obat yang diramu dari tanaman-tanaman tradisonal berkhasiat yang digunakan untuk pengobatan penyakit')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0009', N'Obat Analgesik', N'Obat Pereda Nyeri')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0010', N'Obat Antibakteri', N'Obat yang Mengatasi Infeksi Bakteri')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0011', N'Obat Antidepresan', N'Obat untuk menangani gejala depresi')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0012', N'Obat Anti Kejang', N'Obat Mencegah dan mengatasi kejang atau serangan epilepsi')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0013', N'Obat Antijamur', N'Obat yang mengatasi infeksi jamur')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0014', N'Antiemetik', N'Obat yang mengatasi mual dan muntah')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0015', N'Obat Antihistamin', N'Obat untuk melawan efek histamin sebagai penyebab alergi')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0016', N'Obat Anti inflamasi', N'Obat yang mengatasi peradangan')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0017', N'Obat Tidur', N'Obat yang mampu mengatasi gangguan tidur ')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0018', N'Obat Anestetik', N'Obat bius')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0019', N'Obat Antivirus', N'Obat yang diperuntukkan mengobati infeksi virus')
INSERT [dbo].[mKategori] ([id_kategori], [nama_kategori], [keterangan]) VALUES (N'KTG0020', N'Obat Anticemas', N'Obat ini mengurangi kecemasan dan mengendurkan otot-otot tubuh')
GO
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0001', N'Paramex', N'KTG0003', N'HCL', N'Obat Tablet', N'Dewasa', CAST(N'2022-09-24' AS Date), 100, 5000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0002', N'Konidin', N'KTG0003', N'Paracetamol ', N'Obat Sirup', N'Dewasa', CAST(N'2022-10-01' AS Date), 85, 6000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0003', N'OBH', N'KTG0001', N'Paracetamol 150mg,Ammonium Chloride 50mg', N'Obat Sirup', N'Dewasa', CAST(N'2023-12-02' AS Date), 50, 18000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0004', N'Amfetamin ', N'KTG0011', N'Bahan kimia 1-fenil-2-aminopropane', N'Obat Tablet', N'Dewasa', CAST(N'2026-02-03' AS Date), 200, 25000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0005', N'Antangin', N'KTG0008', N'Bahan herbal : daun, jahe, daun sembung, ginseng, peppermint', N'Obat Cair', N'Dewasa', CAST(N'2025-12-11' AS Date), 150, 5000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0006', N'Bodrex', N'KTG0003', N'Paracetamol', N'Obat Tablet', N'Dewasa', CAST(N'2025-08-21' AS Date), 130, 4000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0007', N'Bisolvon', N'KTG0003', N'Bromhexine HCL ,100mg guaifenesin', N'Obat Sirup', N'Dewasa', CAST(N'2025-11-27' AS Date), 120, 27000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0008', N'Polysilane', N'KTG0003', N'200mg alumunium hidroksida, 200mg magnesium hidoksida, 80mg dimethicone', N'Obat Tablet', N'Dewasa', CAST(N'2024-07-20' AS Date), 100, 10000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0009', N'Sanmol', N'KTG0009', N'Paracetamol', N'Obat Tablet', N'Dewasa', CAST(N'2025-02-05' AS Date), 100, 5000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0010', N'Alganax', N'KTG0004', N'Alprazolam', N'Obat Tablet', N'Dewasa', CAST(N'2025-06-11' AS Date), 110, 20000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0011', N'Ambiopi', N'KTG0010', N'Ampicilin 500mg', N'Obat Tablet', N'Dewasa', CAST(N'2026-08-13' AS Date), 100, 25000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0012', N'Antalgin', N'KTG0016', N'Metamizole 500mg', N'Obat Tablet', N'Dewasa', CAST(N'2026-08-14' AS Date), 60, 11000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0013', N'Kalpanax Salep', N'KTG0003', N'miconazole nitrate, asam salisilat, asam benzoate', N'Obat Oles', N'Dewasa', CAST(N'2025-11-27' AS Date), 110, 26000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0014', N'Bodrexin', N'KTG0003', N' Acetylsalicylic Acid 80 mg', N'Obat Tablet', N'Semua Umur', CAST(N'2029-10-31' AS Date), 150, 5000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0015', N'Cetrizine', N'KTG0004', N'Cetirizine Hydrochloride(HCL)', N'Obat Tablet', N'Semua Umur', CAST(N'2026-09-24' AS Date), 80, 10000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0016', N'Diapet ', N'KTG0008', N'240 mg daun jambu biji, 204 mg rimpang kunyit, 84 mg buah mojokeling, dan 72 mg kulit buah delima', N'Obat Kapsul', N'Semua Umur', CAST(N'2030-06-26' AS Date), 100, 6000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0017', N'Benazepril ', N'KTG0004', N'Benazepril ', N'Obat Tablet', N'Dewasa', CAST(N'2025-10-07' AS Date), 100, 6000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0018', N'Healthy Care Vitamin D3 1000IU', N'KTG0003', N'Vitamin D', N'Obat Tablet', N'Semua Umur', CAST(N'2025-06-10' AS Date), 95, 35000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0019', N'Enervon C', N'KTG0003', N'Vitamin C', N'Obat Kapsul', N'Semua Umur', CAST(N'2025-11-19' AS Date), 190, 35000.0000)
INSERT [dbo].[mObat] ([id_obat], [nama_obat], [id_kategori], [kandungan], [jenis], [dosis], [exp_date], [stok], [harga]) VALUES (N'OBT0020', N'IPI Vitamin B Complex ', N'KTG0003', N'Vitamin B', N'Obat Kapsul', N'Semua Umur', CAST(N'2022-11-24' AS Date), 100, 31000.0000)
GO
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0001', N'CUS0016', N'Dr Tirta', N'Rs Thamrin ', N'Paracetamol 500mg', CAST(N'2022-07-18' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0002', N'CUS0011', N'Dr Udin', N'RS Hermina Mekarsari', N'Vitamin D 100mg', CAST(N'2022-07-18' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0003', N'CUS0014', N'Dr Linux', N'Rs Permata Cibubur', N'Vitamin C 100mg, Paracetamol 500mg', CAST(N'2022-07-18' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0004', N'CUS0010', N'Dr Rizaldi', N'Rs Bakti Husada ', N'Vitamin D, Paracetamol ', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0005', N'CUS0018', N'Dr Galuh ', N'Rs Bakti Medika ', N'Cetrizine 100mg, Paracetamol 500mg, Vitamin C', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0006', N'CUS0021', N'Dr Agit', N'RSIA Yusuf Sadikin', N'Amfetamin, Vitamin B', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0007', N'CUS0003', N'Dr Santoso', N'Rs Permata Cileungsi ', N'Bromhexine HCL', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0008', N'CUS0005', N'Dr Risa Saraswati', N'RS Cipto ', N'Ampicilin 500mg ', CAST(N'2022-07-19' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0009', N'CUS0007', N'Dr Dwi', N'RS Mary Cikarang', N'Paracetamol , Bromehexine HCL', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0010', N'CUS0008', N'Dr Rian ', N'Rs Lippo Cikarang ', N'Paracetamol, Vitamin D, Vitamin C', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0011', N'CUS0009', N'Dr Kirana', N'RS Husada Madiun ', N'Benazepril', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0012', N'CUS0011', N'Dr Tirto', N'Rs Hermina Bekasi', N'Benazepril, Vitamin C', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0013', N'CUS0012', N'Dr Ramadhan', N'Klink Beta Medika ', N'Metamizole , Vitamin B', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0014', N'CUS0014', N'Dr Khairunnisa ', N'Klink Lippo Cibatu', N'Vitamin C dan Vitamin D', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0015', N'CUS0015', N'Dr Junian', N'Klinik Pramita', N'Vitamin E ', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0016', N'CUS0017', N'Dr Maylia', N'Klink Pramita ', N'Paracetamol , HCL', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0017', N'CUS0019', N'Dr Ell', N'Klinik Permata ', N'HCL dan Paracetamol ', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0018', N'CUS0021', N'Dr Irawan', N'Klinik Kasih Ibu', N'Vitamin B', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0019', N'CUS0003', N'Dr Samuel', N'Klink Kasih Ibu ', N'Ampicilin 500mg, Paracetamol 500mg', CAST(N'2022-07-20' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0020', N'CUS0010', N'Dr Seftian ', N'Klinik Kasih Beta', N'Benazepril', CAST(N'2026-08-06' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0021', N'CUS0021', N'dr. Youmal Dwi', N'Klinik Sehat Banget', N'Paracetamol 500gr,
Analgesik 250gr', CAST(N'2022-08-01' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0022', N'CUS0026', N'dr. Jujun Junaedi', N'Klinik Sehat Banget', N'Paracetamol 500gr,
Analgesik 500gr,
Vitamin C', CAST(N'2022-08-01' AS Date))
INSERT [dbo].[mResep] ([id_resep], [id_customer], [nama_dokter], [praktek], [kandungan], [tgl_input]) VALUES (N'RSP0023', N'CUS0026', N'dr. Ririn Sukiyah', N'Klinik Bojong Sawah', N'Paracetamol 500gr,
Analgesik 250gr,
Vitamin C 250gr,
Vitamin D 250gr', CAST(N'2022-08-01' AS Date))
GO
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0001', N'PT Jaya Sentosa Mandiri', N'0813187929292', N'Tangerang, Banten', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0002', N'PT Kimia Abadi', N'08131928292', N'Bekasi Barat', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0003', N'CV Acep Herbal ', N'02180876272', N'Tasikmalaya, Jawa Barat', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0004', N'PT Kimia Abadi Sejati', N'02157829421', N'Bogor, Jawa Barat', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0005', N'CV Berkah Abadi', N'02178690282', N'Bekasi Kota', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0006', N'CV Berkah Abadi', N'021892122', N'Bogor, Jawa Barat', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0007', N'PT Maju Bersama', N'0212872920', N'Jakarta Timur', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0008', N'PT Kalbe Farma Tbk', N'02187690283', N'Jakarta Pusat', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0009', N'PT Merck Indonesia', N'02190383992', N'Jakarta Barat', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0010', N'PT Darya Varia', N'0219830202', N'Jakarta Selatan', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0011', N'PT Indofarma', N'02147892982', N'Jakarta Timur', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0012', N'PT Muncul Tbk', N'02198323932', N'Bekasi, Jawa Barat', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0013', N'PT Pharos Tbk', N'021827392', N'Tangerang, Banten', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0014', N'PT Pyridam Farma Tbk', N'08127289292', N'Yogyakarta', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0015', N'PT Afiat', N'0822661382', N'Cimindi, Jawa Barat', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0016', N'PT Actavis Indonesia', N'0218710044', N'Jl. Raya Bogor Km 28', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0017', N'PT AstraZeneca Indonesia', N'0343631761', N'Jakarta Selatan', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0018', N'PT Pfizer Indonesia', N'0218710311', N'Jakarta Timur', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0019', N'PT Meiji Indonesia', N'02121383388', N'Jakarta Barat', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0020', N'PT Sanbe Farma', N'0212938922', N'Jakarta Selatan', N'Tidak Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0021', N'CV Sumber Asih', N'021892182190', N'Tangerang, Banten', N'Tidak Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0022', N'Toko Maju Mundur', N'087809871234', N'Jl. Kenanga Raya No.5
Kayuringin Jaya, Bekasi Selatan
Kota Bekasi 17144', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0023', N'Drugs Store 1', N'Jakarta Barat', N'0895123456789', N'Aktif')
INSERT [dbo].[mSupplier] ([id_supplier], [nama_supplier], [telp_supplier], [alamat], [status_supplier]) VALUES (N'SUP0024', N'Toko Obat Herbal Alami', N'08912345678', N'Jl. Burangrang No.3
Kayuringin Jaya, Bekasi Selatan
Kota Bekasi 17144', N'Tidak Aktif')
GO
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR001', N'ADMIN', N'admin', N'admin', N'Admin', N'08131827282', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR002', N'KASIR', N'kasir', N'kasir', N'Kasir', N'0813718212', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR003', N'KEPALA', N'kepala', N'kepala', N'Kepala', N'08131929292', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR004', N'Youmal Dwi Santoso', N'youmal', N'youmal', N'Admin', N'081384427623', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR005', N'Ariza Diva Rahmansyah', N'ariza', N'ariza', N'Kasir', N'08123992238', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR006', N'Tirtra Indra', N'tirta', N'tirta', N'Manager', N'081712812912', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR007', N'Juan', N'juan', N'juan123', N'Admin', N'0813192822', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR008', N'Dito', N'dito', N'dito567', N'Kasir', N'0812172182', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR009', N'Gema', N'gema123', N'gema123', N'Manager', N'081731292929', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR010', N'Faris ', N'faris098', N'faris', N'Admin', N'08768229923', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR011', N'Rian', N'rian12', N'rian34', N'Kasir', N'081291291', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR012', N'Rafli', N'rafli', N'rafli9012', N'Manager', N'087927292', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR013', N'Sashi', N'sashi980', N'sashi', N'Admin', N'0898927292', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR014', N'Seftia', N'seft', N'seftia', N'Kasir', N'08192922923', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR015', N'Ika Maylia', N'ika', N'ika970', N'Manager', N'081291922232', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR016', N'Abdullah', N'abdul', N'abdul875', N'Admin', N'0818912202', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR017', N'Ocky ', N'ocky122', N'ocky567', N'Kasir', N'0812912910', N'Aktif')
INSERT [dbo].[mUser] ([id_user], [nama_user], [username], [password], [jabatan], [telp_user], [status_user]) VALUES (N'USR018', N'Ela', N'ela980', N'ela678', N'Manager', N'0819207281920', N'Aktif')
GO
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0001', N'OBT0016', 10, 60000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0001', N'OBT0018', 10, 350000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0002', N'OBT0006', 10, 40000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0002', N'OBT0014', 10, 50000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0003', N'OBT0001', 35, 175000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0003', N'OBT0003', 38, 684000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0004', N'OBT0002', 19, 114000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0005', N'OBT0005', 50, 250000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0006', N'OBT0019', 50, 1750000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0007', N'OBT0013', 50, 1300000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0007', N'OBT0017', 50, 300000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0008', N'OBT0011', 50, 1250000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0009', N'OBT0009', 50, 250000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0010', N'OBT0008', 50, 500000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0011', N'OBT0014', 40, 200000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0012', N'OBT0018', 40, 1400000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0013', N'OBT0010', 10, 200000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0013', N'OBT0012', 10, 110000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0014', N'OBT0007', 40, 1080000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0015', N'OBT0004', 10, 250000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0015', N'OBT0007', 50, 1350000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0016', N'OBT0013', 10, 260000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0017', N'OBT0015', 10, 100000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0018', N'OBT0015', 10, 100000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0018', N'OBT0016', 10, 60000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0019', N'OBT0015', 10, 100000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0019', N'OBT0020', 10, 310000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0020', N'OBT0019', 10, 350000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0021', N'OBT0002', 50, 300000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0021', N'OBT0006', 90, 160000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0022', N'OBT0014', 50, 250000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0022', N'OBT0019', 90, 1400000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0023', N'OBT0003', 50, 900000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0023', N'OBT0005', 50, 250000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0024', N'OBT0016', 30, 180000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0024', N'OBT0020', 40, 1240000.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0025', N'OBT0001', 50, 212500.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0026', N'OBT0004', 150, 1062500.0000)
INSERT [dbo].[tbDetailPembelian] ([id_pembelian], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRB0026', N'OBT0010', 50, 850000.0000)
GO
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0002', N'OBT0002', 1, 6000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0003', N'OBT0002', 1, 6000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0004', N'OBT0001', 2, 5000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0005', N'OBT0002', 2, 6000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0006', N'OBT0003', 2, 18000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0007', N'OBT0003', 2, 18000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0008', N'OBT0003', 2, 18000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0009', N'OBT0003', 2, 18000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0010', N'OBT0003', 2, 18000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0011', N'OBT0001', 1, 5000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0011', N'OBT0003', 1, 18000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0012', N'OBT0001', 1, 5000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0012', N'OBT0003', 1, 18000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0013', N'OBT0009', 10, 5000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0013', N'OBT0012', 6, 11000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0014', N'OBT0005', 3, 5000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0014', N'OBT0014', 5, 5000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0015', N'OBT0007', 3, 27000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0016', N'OBT0015', 2, 10000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0017', N'OBT0013', 3, 26000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0018', N'OBT0008', 3, 10000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0018', N'OBT0016', 2, 6000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0019', N'OBT0009', 2, 5000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0019', N'OBT0019', 5, 35000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0020', N'OBT0007', 5, 27000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0021', N'OBT0002', 5, 30000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0022', N'OBT0003', 10, 180000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0022', N'OBT0004', 10, 250000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0023', N'OBT0007', 1, 27000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0024', N'OBT0002', 10, 60000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0024', N'OBT0018', 5, 175000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0024', N'OBT0019', 10, 350000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0025', N'OBT0003', 1, 18000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0025', N'OBT0005', 10, 50000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0026', N'OBT0007', 19, 513000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0026', N'OBT0018', 5, 175000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0027', N'OBT0015', 10, 100000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0027', N'OBT0016', 10, 60000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0028', N'OBT0003', 40, 720000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0029', N'OBT0003', 40, 720000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0030', N'OBT0003', 40, 720000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0031', N'OBT0006', 20, 80000.0000)
INSERT [dbo].[tbDetailPenjualan] ([id_penjualan], [id_obat], [jumlah_obat], [jumlah_harga]) VALUES (N'TRJ0031', N'OBT0007', 19, 513000.0000)
GO
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0001', N'SUP0003', CAST(N'2022-07-20' AS Date), 410000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0002', N'SUP0008', CAST(N'2022-07-20' AS Date), 90000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0003', N'SUP0004', CAST(N'2022-07-20' AS Date), 859000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0004', N'SUP0001', CAST(N'2022-07-20' AS Date), 114000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0005', N'SUP0018', CAST(N'2022-07-20' AS Date), 250000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0006', N'SUP0019', CAST(N'2022-07-20' AS Date), 1750000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0007', N'SUP0016', CAST(N'2022-07-20' AS Date), 1600000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0008', N'SUP0021', CAST(N'2022-07-20' AS Date), 1250000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0009', N'SUP0020', CAST(N'2022-07-20' AS Date), 250000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0010', N'SUP0015', CAST(N'2022-07-20' AS Date), 500000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0011', N'SUP0002', CAST(N'2021-08-20' AS Date), 200000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0012', N'SUP0006', CAST(N'2021-08-20' AS Date), 1400000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0013', N'SUP0016', CAST(N'2021-08-20' AS Date), 310000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0014', N'SUP0015', CAST(N'2021-08-20' AS Date), 1080000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0015', N'SUP0017', CAST(N'2021-08-20' AS Date), 1600000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0016', N'SUP0020', CAST(N'2021-08-20' AS Date), 260000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0017', N'SUP0012', CAST(N'2021-08-20' AS Date), 100000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0018', N'SUP0014', CAST(N'2021-08-20' AS Date), 160000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0019', N'SUP0012', CAST(N'2021-08-20' AS Date), 410000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0020', N'SUP0013', CAST(N'2021-08-20' AS Date), 350000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0021', N'SUP0003', CAST(N'2022-07-20' AS Date), 660000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0022', N'SUP0002', CAST(N'2022-08-01' AS Date), 3400000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0023', N'SUP0002', CAST(N'2022-08-01' AS Date), 1150000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0024', N'SUP0004', CAST(N'2022-08-01' AS Date), 1420000)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0025', N'SUP0003', CAST(N'2022-08-01' AS Date), 212500)
INSERT [dbo].[tbTrPembelian] ([id_pembelian], [id_supplier], [tgl_pembelian], [total_harga_beli]) VALUES (N'TRB0026', N'SUP0013', CAST(N'2022-08-01' AS Date), 1912500)
GO
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0002', NULL, CAST(N'2022-07-14' AS Date), 6000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0003', NULL, CAST(N'2022-07-17' AS Date), 6000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0004', NULL, CAST(N'2022-07-17' AS Date), 10000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0005', NULL, CAST(N'2022-07-17' AS Date), 12000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0006', NULL, CAST(N'2022-07-18' AS Date), 36000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0007', NULL, CAST(N'2022-07-18' AS Date), 36000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0008', NULL, CAST(N'2022-07-18' AS Date), 41000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0009', NULL, CAST(N'2022-07-18' AS Date), 41000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0010', NULL, CAST(N'2022-07-18' AS Date), 41000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0011', NULL, CAST(N'2021-07-18' AS Date), 23000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0012', N'RSP0001', CAST(N'2021-07-18' AS Date), 23000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0013', N'RSP0014', CAST(N'2021-07-20' AS Date), 116000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0014', NULL, CAST(N'2021-07-20' AS Date), 40000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0015', NULL, CAST(N'2021-07-20' AS Date), 81000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0016', N'RSP0016', CAST(N'2021-07-20' AS Date), 20000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0017', NULL, CAST(N'2021-07-20' AS Date), 78000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0018', NULL, CAST(N'2021-07-20' AS Date), 42000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0019', NULL, CAST(N'2021-07-20' AS Date), 185000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0020', NULL, CAST(N'2021-07-20' AS Date), 135000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0021', N'RSP0021', CAST(N'2022-07-20' AS Date), 30000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0022', N'RSP0002', CAST(N'2022-08-01' AS Date), 430000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0023', N'RSP0007', CAST(N'2022-08-01' AS Date), 27000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0024', N'RSP0023', CAST(N'2022-08-01' AS Date), 585000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0025', NULL, CAST(N'2022-08-01' AS Date), 50000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0026', NULL, CAST(N'2022-08-01' AS Date), 688000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0027', NULL, CAST(N'2022-08-01' AS Date), 160000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0028', NULL, CAST(N'2022-08-01' AS Date), 720000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0029', NULL, CAST(N'2022-08-01' AS Date), 720000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0030', NULL, CAST(N'2022-08-01' AS Date), 720000.0000)
INSERT [dbo].[tbTrPenjualan] ([id_penjualan], [id_resep], [tgl_penjualan], [total_harga_jual]) VALUES (N'TRJ0031', NULL, CAST(N'2022-08-01' AS Date), 593000.0000)
GO
ALTER TABLE [dbo].[mObat]  WITH CHECK ADD  CONSTRAINT [FK_ObKategori] FOREIGN KEY([id_kategori])
REFERENCES [dbo].[mKategori] ([id_kategori])
GO
ALTER TABLE [dbo].[mObat] CHECK CONSTRAINT [FK_ObKategori]
GO
ALTER TABLE [dbo].[mResep]  WITH CHECK ADD  CONSTRAINT [FK_ResCustomer] FOREIGN KEY([id_customer])
REFERENCES [dbo].[mCustomer] ([id_customer])
GO
ALTER TABLE [dbo].[mResep] CHECK CONSTRAINT [FK_ResCustomer]
GO
ALTER TABLE [dbo].[tbDetailPembelian]  WITH CHECK ADD FOREIGN KEY([id_obat])
REFERENCES [dbo].[mObat] ([id_obat])
ON UPDATE CASCADE
GO
ALTER TABLE [dbo].[tbDetailPembelian]  WITH CHECK ADD FOREIGN KEY([id_pembelian])
REFERENCES [dbo].[tbTrPembelian] ([id_pembelian])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[tbTrPembelian]  WITH CHECK ADD FOREIGN KEY([id_supplier])
REFERENCES [dbo].[mSupplier] ([id_supplier])
ON UPDATE CASCADE
GO
/****** Object:  StoredProcedure [dbo].[sp_CariObat]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE[dbo].[sp_CariObat]
    @nama varchar(50)
AS
BEGIN

	SELECT id_obat FROM mObat
	WHERE nama_obat = @nama 
END
GO
/****** Object:  StoredProcedure [dbo].[sp_CariSupplier]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE[dbo].[sp_CariSupplier]
    @nama varchar(50)
AS
BEGIN

	SELECT id_supplier FROM mSupplier
	WHERE nama_supplier = @nama AND status_supplier = 'Aktif'
END
GO
/****** Object:  StoredProcedure [dbo].[sp_DeleteKategori]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_DeleteKategori]
	@id_kategori char(6) 
AS
BEGIN
	DELETE FROM mKategori where id_kategori=@id_kategori
END
GO
/****** Object:  StoredProcedure [dbo].[sp_HapusCustomer]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--5. Delete Customer
CREATE PROCEDURE [dbo].[sp_HapusCustomer]
	@id_customer	char(7)
AS
BEGIN
	UPDATE mCustomer SET
		status_customer = 'Tidak Aktif'
	WHERE id_customer = @id_customer
END
GO
/****** Object:  StoredProcedure [dbo].[sp_HapusObat]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--6. Delete Obat
CREATE PROCEDURE [dbo].[sp_HapusObat]
	@id_obat char(7) 
AS
BEGIN
	DELETE FROM mObat where id_obat=@id_obat
END
GO
/****** Object:  StoredProcedure [dbo].[sp_HapusResep]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--7. Delete Resep
CREATE PROCEDURE [dbo].[sp_HapusResep]
	@id_resep	char(7)
AS
BEGIN
	DELETE FROM mResep WHERE id_resep = @id_resep
END
GO
/****** Object:  StoredProcedure [dbo].[sp_HapusSupplier]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--4. Delete Supplier
CREATE PROCEDURE [dbo].[sp_HapusSupplier]
	@id_supplier	char(7)
AS
BEGIN
	UPDATE mSupplier SET
		status_supplier = 'Tidak Aktif'
	WHERE id_supplier = @id_supplier
END
GO
/****** Object:  StoredProcedure [dbo].[sp_HapusUser]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--2. Delete User
CREATE PROCEDURE [dbo].[sp_HapusUser]
	@id_user	char(7)
AS
BEGIN
	UPDATE mUser
	SET status_user = 'Tidak Aktif'
	WHERE id_user = @id_user
END
GO
/****** Object:  StoredProcedure [dbo].[sp_InputCustomer]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_InputCustomer]
	@id_customer		CHAR	(6),
	@nama_customer		VARCHAR	(50),
	@telp_customer		VARCHAR	(13)
AS
BEGIN
	INSERT INTO mCustomer
		VALUES (@id_customer, @nama_customer, @telp_customer, null)
END
GO
/****** Object:  StoredProcedure [dbo].[sp_InputKategori]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_InputKategori]
	@id_kategori		CHAR	(6),
	@nama_kategori		VARCHAR	(50),
	@keterangan			VARCHAR (100)
AS
BEGIN
	INSERT INTO mKategori
		VALUES (@id_kategori, @nama_kategori, @keterangan)
END
GO
/****** Object:  StoredProcedure [dbo].[sp_InputObat]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_InputObat]
	@id_obat			CHAR	(6),
	@nama_obat			VARCHAR	(50),
	@id_kategori		CHAR	(6),
	@kandungan			VARCHAR	(100),
	@jenis				VARCHAR	(50),
	@dosis				VARCHAR (100),
	@exp_date			date,
	@stok				INT			,
	@harga				MONEY		
AS
BEGIN
	INSERT INTO mObat
		VALUES (@id_obat, @nama_obat, @id_kategori, @kandungan, @jenis, @dosis, @exp_date, @stok, @harga)
END
GO
/****** Object:  StoredProcedure [dbo].[sp_InputResep]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- 7. Input Resep
CREATE PROCEDURE [dbo].[sp_InputResep]
	@id_resep			CHAR	(6),
	@id_customer		CHAR	(6),
	@nama_dokter		VARCHAR (50),
	@praktek			VARCHAR	(50),
	@kandungan			VARCHAR (200),
	@tgl_input			DATE
AS
BEGIN
	INSERT INTO mResep
		VALUES (@id_resep, @id_customer, @nama_dokter, @praktek, @kandungan, @tgl_input)
END
GO
/****** Object:  StoredProcedure [dbo].[sp_InputSupplier]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_InputSupplier]
	@id_supplier		CHAR	(6),
	@nama_supplier		VARCHAR	(50),
	@telp_supplier		VARCHAR	(13),
	@alamat				VARCHAR	(100)
AS
BEGIN
	INSERT INTO mSupplier
		VALUES (@id_supplier, @nama_supplier, @telp_supplier, @alamat, null)
END
GO
/****** Object:  StoredProcedure [dbo].[sp_InputTrPembelian]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--Transaksi Beli 
CREATE PROCEDURE [dbo].[sp_InputTrPembelian]
	@id_pembelian			CHAR	(7),
	@id_supplier			CHAR    (7),
	@tgl_pembelian			DATE,
	@total_harga_beli		money

AS
BEGIN
	INSERT INTO tbTrPembelian
	VALUES(@id_pembelian, @id_supplier, @tgl_pembelian, @total_harga_beli)
END
GO
/****** Object:  StoredProcedure [dbo].[sp_InputTrPenjualan]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_InputTrPenjualan]
	@id_penjualan					CHAR	(7),
	@id_resep						CHAR	(7),
	@tgl_penjualan					DATE,
	@total_harga_beli				MONEY
AS
BEGIN
	INSERT INTO tbTrPenjualan
	VALUES(@id_penjualan, @id_resep, @tgl_penjualan, @total_harga_beli)
end
GO
/****** Object:  StoredProcedure [dbo].[sp_InputTrPenjualan1]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_InputTrPenjualan1]
	@id_penjualan					CHAR	(7),
	@tgl_penjualan					DATE,
	@total_harga_beli				MONEY
AS
BEGIN
DECLARE @id_resep CHAR(7);
	SET @id_resep = NULL;

	INSERT INTO tbTrPenjualan
	VALUES(@id_penjualan, @id_resep,@tgl_penjualan, @total_harga_beli)
end
GO
/****** Object:  StoredProcedure [dbo].[sp_InputUser]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- 2. Input User
CREATE PROCEDURE [dbo].[sp_InputUser]
	@id_user			CHAR	(7)	,
	@nama_user			VARCHAR	(50)	,
	@username			VARCHAR	(20),
	@password			VARCHAR	(20),
	@jabatan			VARCHAR (20),
	@telp_user			VARCHAR	(13)
	
AS
BEGIN
	INSERT INTO mUser
		VALUES (@id_user, @nama_user, @username, @password, @jabatan, @telp_user, null)
END
GO
/****** Object:  StoredProcedure [dbo].[sp_LoadCustomer]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--3. Load Customer
CREATE PROCEDURE [dbo].[sp_LoadCustomer]
AS
BEGIN
	SELECT * FROM mCustomer WHERE status_customer = 'Aktif'
END
GO
/****** Object:  StoredProcedure [dbo].[sp_LoadSupplier]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_LoadSupplier]
AS
BEGIN
	SELECT * FROM mSupplier WHERE status_supplier = 'Aktif'
END
GO
/****** Object:  StoredProcedure [dbo].[sp_LoadUser]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--1. Load User
CREATE PROCEDURE [dbo].[sp_LoadUser]
AS
BEGIN
	SELECT * FROM mUser WHERE status_user = 'Aktif'
END
GO
/****** Object:  StoredProcedure [dbo].[sp_login]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_login]
	@username varchar(20),
	@password varchar(20)
AS
	BEGIN
		SELECT jabatan
		FROM mUser
		WHERE username = @username 
		AND [password] = @password
		AND status_user = 'Aktif'
	END
GO
/****** Object:  StoredProcedure [dbo].[sp_loginRole]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_loginRole]
	@id_user varchar(7)
AS
	BEGIN
		SELECT jabatan
		FROM mUser
		WHERE id_user = @id_user
	END
GO
/****** Object:  StoredProcedure [dbo].[sp_UpdateCustomer]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- 4. Update Customer
CREATE PROCEDURE [dbo].[sp_UpdateCustomer]
	@id_customer char(6),
	@nama_customer varchar(50),
	@telp_customer varchar(13)
AS
BEGIN
	UPDATE mCustomer SET
		nama_customer = @nama_customer,
		telp_customer = @telp_customer
	WHERE id_customer = @id_customer
END
GO
/****** Object:  StoredProcedure [dbo].[sp_UpdateKategori]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--5. Update Kategori
CREATE PROCEDURE [dbo].[sp_UpdateKategori]
	@id_kategori char(7),
	@nama_kategori varchar(50),
	@keterangan varchar(100)
AS
BEGIN
	UPDATE mKategori SET
		nama_kategori = @nama_kategori,
		keterangan = @keterangan
	WHERE id_kategori = @id_kategori
END
GO
/****** Object:  StoredProcedure [dbo].[sp_UpdateObat]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--6. Update Obat
CREATE PROCEDURE [dbo].[sp_UpdateObat]
	@id_obat			CHAR	(7),
	@nama_obat			VARCHAR	(50),
	@id_kategori		CHAR	(7),
	@kandungan			VARCHAR	(100),
	@jenis				VARCHAR	(50),
	@dosis				VARCHAR (50),
	@exp_date			date,
	@stok				INT			,
	@harga				MONEY	
AS
BEGIN
	UPDATE mObat SET
		nama_obat = @nama_obat,
		id_kategori = @id_kategori,
		kandungan = @kandungan,
		jenis = @jenis,
		dosis = @dosis,
		exp_date = @exp_date,
		stok = @stok,
		harga = @harga

	WHERE id_obat = @id_obat
END
GO
/****** Object:  StoredProcedure [dbo].[sp_UpdateResep]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
--7. Update Resep
CREATE PROCEDURE [dbo].[sp_UpdateResep]
	@id_resep			CHAR	(7),
	@id_customer		CHAR	(7),
	@nama_dokter		VARCHAR (50),
	@praktek			VARCHAR	(50),
	@kandungan			VARCHAR (200),
	@tgl_input			DATE
AS
BEGIN
	UPDATE mResep SET
		id_customer = @id_customer,
		nama_dokter = @nama_dokter,
		praktek = @praktek,
		kandungan = @kandungan,
		tgl_input = @tgl_input
	WHERE id_resep = @id_resep
END
GO
/****** Object:  StoredProcedure [dbo].[sp_UpdateStock1]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_UpdateStock1]
	@id1	VARCHAR(7),
	@id2	VARCHAR(7),
	@qty	INT,
	@harga	MONEY
AS
BEGIN
	UPDATE mObat SET
	stok = stok + @qty
	WHERE id_obat = @id2

	INSERT INTO tbDetailPembelian VALUES
	(@id1, @id2, @qty, @harga)
END
GO
/****** Object:  StoredProcedure [dbo].[sp_UpdateStock2]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_UpdateStock2]
	@id1	VARCHAR(7),
	@id2	VARCHAR(7),
	@qty	INT,
	@harga	MONEY
	
AS
BEGIN
	UPDATE mObat SET
	stok = stok - @qty
	WHERE id_obat = @id2

	INSERT INTO tbDetailPenjualan VALUES
	(@id1, @id2, @qty, @harga)
END
GO
/****** Object:  StoredProcedure [dbo].[sp_UpdateSupplier]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_UpdateSupplier]
	@id_supplier char(6),
	@nama_supplier varchar(50),
	@telp_supplier varchar(13),
	@alamat varchar(100)
AS
BEGIN
	UPDATE mSupplier SET
		nama_supplier = @nama_supplier,
		telp_supplier= @telp_supplier,
		alamat = @alamat
	WHERE id_supplier = @id_supplier
END
GO
/****** Object:  StoredProcedure [dbo].[sp_UpdateUser]    Script Date: 01/08/2022 20:26:42 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_UpdateUser]
	@id_user		char(7),
	@nama_user		VARCHAR (50),
	@username		VARCHAR	(20),				
	@password		VARCHAR	(20),			
	@jabatan		CHAR	(7),				
	@telp_user		VARCHAR	(13)
AS
BEGIN
	UPDATE mUser SET
		nama_user = @nama_user,
		username = @username,
		password = @password,
		jabatan = @jabatan,
		telp_user = @telp_user
	WHERE id_user = @id_user
END
GO
USE [master]
GO
ALTER DATABASE [dbHalotek] SET  READ_WRITE 
GO
