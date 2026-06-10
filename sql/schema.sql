CREATE DATABASE IF NOT EXISTS perpustakaan_db;
DROP TABLE IF EXISTS peminjaman;
DROP TABLE IF EXISTS buku;
DROP TABLE IF EXISTS members;
USE perpustakaan_db;

CREATE TABLE buku (
	id_buku				INT PRIMARY KEY AUTO_INCREMENT,
	judul 				VARCHAR(255) NOT NULL,
	penulis				VARCHAR(255) NOT NULL,
	genre				VARCHAR(100),
	tipe_buku			ENUM('PELAJARAN', 'FIKSI', 'JURNAL') NOT NULL,
	info_buku 			VARCHAR(255),
	maks_peminjaman		INT NOT NULL,
	harga_beli			BIGINT NOT NULL DEFAULT 0,
	status_buku			ENUM('TERSEDIA', 'DIPINJAM', 'DIPESAN', 'RUSAK') DEFAULT 'TERSEDIA',
	is_langka 			TINYINT DEFAULT 0,
	is_promo 			TINYINT DEFAULT 0,
	is_laris			TINYINT DEFAULT 0
);

CREATE TABLE members (
	id_member		INT PRIMARY KEY AUTO_INCREMENT,
	nama_lengkap 	VARCHAR(255) NOT NULL,
	email 			VARCHAR(255) UNIQUE NOT NULL,
	phone 			VARCHAR(20),
	is_blokir		TINYINT DEFAULT 0
);

CREATE TABLE peminjaman (
	id_peminjaman 			INT PRIMARY KEY AUTO_INCREMENT,
	id_member 				INT NOT NULL,
	id_buku 			    INT NOT NULL,
	tanggal_peminjaman 	DATE NOT NULL DEFAULT (CURDATE()),
	tanggal_jatuh_tempo	DATE NOT NULL,
	tanggal_pengembalian	DATE,
	biaya_sewa				BIGINT DEFAULT 0,
	biaya_deposit			BIGINT DEFAULT 0, 
	denda						BIGINT DEFAULT 0,
	status_peminjaman  	ENUM('AKTIF', 'DIKEMBALIKAN', 'TERLAMBAT') DEFAULT 'AKTIF',
	
	FOREIGN KEY (id_member) REFERENCES members(id_member),
	FOREIGN KEY (id_buku) REFERENCES buku(id_buku)
);

-- DUMMY DATA
INSERT INTO members (nama_lengkap, email, phone, is_blokir) VALUES
-- 	id_member	nama_lengkap	email				nomor hp		is_blokir
    ('Budi Santoso',  'budi@email.com', '081234567890', 0),
    ('Siti Rahayu',   'siti@email.com', '089876543210', 0),
    ('Ahmad Fauzi',   'ahmad@email.com', '085612345678', 0);

INSERT INTO buku (judul, penulis, genre, tipe_buku, info_buku, maks_peminjaman, harga_beli, status_buku, is_langka, is_promo, is_laris)VALUES
--  judul                          penulis              genre         tipe       info_buku       maks  harga    status      langka promo laris
    ('Laskar Pelangi',              'Andrea Hirata',     'Drama',      'FIKSI',   NULL,            14,   95000,   'TERSEDIA', 0,     1,    1   ),
    ('Matematika Kelas XII',        'Sukino',            'Sains',      'PELAJARAN','Matematika',   21,   150000,  'TERSEDIA', 0,     0,    0   ),
    ('National Geographic Vol.201', 'Tim NG',            'Sains',      'JURNAL',  'Edisi 201',     7,    50000,   'TERSEDIA', 1,     0,    0   ),
    ('Bumi Manusia',                'Pramoedya A. Toer', 'Sejarah',    'FIKSI',   NULL,            14,   100000,  'TERSEDIA', 1,     1,    1   ),
    ('Kimia Organik Dasar',         'Raymond Chang',     'Sains',      'PELAJARAN','Kimia',        21,   120000,  'TERSEDIA', 0,     0,    0   );
