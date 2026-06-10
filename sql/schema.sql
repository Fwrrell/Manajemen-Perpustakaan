CREATE DATABASE IF NOT EXISTS perpustakaan_db;
USE perpustakaan_db;

CREATE TABLE buku (
	id_buku				VARCHAR(20) PRIMARY KEY,
	judul 				VARCHAR(255) NOT NULL,
	penulis				VARCHAR(255) NOT NULL,
	genre					VARCHAR(100),
	tipe_buku			ENUM('PELAJARAN', 'FIKSI', 'JURNAL', 'KOLEKSI SPESIAL') NOT NULL,
	info_buku 			VARCHAR(255),
	maks_peminjaman	INT NOT NULL,
	status_buku			ENUM('TERSEDIA', 'DIPINJAM', 'DIPESAN', 'RUSAK') DEFAULT 'TERSEDIA',
	is_langka 			TINYINT DEFAULT 0,
	is_promo 			TINYINT DEFAULT 0,
	is_laris				TINYINT DEFAULT 0
);

CREATE TABLE members (
	id_member		VARCHAR(20) PRIMARY KEY,
	nama_lengkap 	VARCHAR(255) NOT NULL,
	email 			VARCHAR(255) UNIQUE NOT NULL,
	phone 			VARCHAR(20),
	is_blokir		TINYINT DEFAULT 0
);

CREATE TABLE peminjaman (
	id_peminjaman 			VARCHAR(20) PRIMARY KEY,
	id_member 				VARCHAR(20) NOT NULL,
	id_buku 					VARCHAR(20) NOT NULL,
	tanggal_peminjaman 	DATE NOT NULL DEFAULT (CURDATE()),
	tanggal_jatuh_tempo	DATE NOT NULL,
	tanggal_pengembalian	DATE,
	biaya_sewa				BIGINT DEFAULT 0,
	biaya_deposit			BIGINT DEFAULT 0, -- kalo buku rare harus deposit
	denda						BIGINT DEFAULT 0,
	status_peminjaman  	ENUM('AKTIF', 'DIKEMBALIKAN', 'TERLAMBAT') DEFAULT 'AKTIF',
	
	FOREIGN KEY (id_member) REFERENCES members(id_member),
	FOREIGN KEY (id_buku) REFERENCES buku(id_buku)
);

-- DUMMY DATA
INSERT INTO members VALUES
-- 	id_member	nama_lengkap	email				nomor hp		is_blokir
    ('MB-001', 'Budi Santoso',  'budi@email.com',   '081234567890', 0),
    ('MB-002', 'Siti Rahayu',   'siti@email.com',   '089876543210', 0),
    ('MB-003', 'Ahmad Fauzi',   'ahmad@email.com',  '085612345678', 0);

INSERT INTO buku VALUES
--  id_buku    judul                          penulis              genre         tipe       info_buku          maks  status      langka promo laris
    ('BK-001', 'Laskar Pelangi',              'Andrea Hirata',     'Drama',      'FIKSI',   NULL,              14,   'TERSEDIA', 0,     1,    1   ),
    ('BK-002', 'Matematika Kelas XII',        'Sukino',            'Sains',      'PELAJARAN','Matematika',     21,   'TERSEDIA', 0,     0,    0   ),
    ('BK-003', 'National Geographic Vol.201', 'Tim NG',            'Sains',      'JURNAL',  'Edisi 201',       7,    'TERSEDIA', 1,     0,    0   ),
    ('BK-004', 'Bumi Manusia',                'Pramoedya A. Toer', 'Sejarah',    'FIKSI',   NULL,              14,   'TERSEDIA', 1,     1,    1   ),
    ('BK-005', 'Kimia Organik Dasar',         'Raymond Chang',     'Sains',      'PELAJARAN','Kimia',          21,   'TERSEDIA', 0,     0,    0   );
