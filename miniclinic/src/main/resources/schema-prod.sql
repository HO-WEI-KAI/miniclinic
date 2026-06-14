-- 1. 前半段：所有的建表語法
CREATE TABLE IF NOT EXISTS doctors (
    doctor_id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    department VARCHAR(20) NOT NULL,
    specialty VARCHAR(100),
    password_hash VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS patients (
    chart_no VARCHAR(20) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    gender VARCHAR(10),
    birth_date DATE,
    phone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS appointments (
    appt_id BIGINT PRIMARY KEY,
    chart_no VARCHAR(20) NOT NULL,
    doctor_id VARCHAR(10) NOT NULL,
    appt_date DATE NOT NULL,
    time_slot VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (chart_no) REFERENCES patients(chart_no),
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)
);

-- 2. 後半段：所有的塞資料語法
INSERT INTO doctors (doctor_id, name, department, specialty, password_hash) VALUES
    ('D001', '陳志明醫師', '家醫科', '一般內科、慢性病管理','$2a$10$XhyEgd4qh5TXJa7NkMg3gOqsJxATykAyJERH7ZqTD7eEPVlcmgewm'),
    ('D002', '林佩君醫師', '內科',   '心臟血管、高血壓', '$2a$10$/x/fVm66HZJWeeYZRUbPp..gS9Czgs3a27RjYQPs75obpRoUWU9ZC'),
    ('D003', '王建華醫師', '復健科', '運動傷害、脊椎復健', '$2a$10$4fZBPZq1NJmqW5MUgOUsqukV6OiTJutAKR/WbiFiQ6PRTjFbNsMFy'),
    ('D004', '李美玲醫師', '小兒科', '兒童感冒、疫苗接種',  '$2a$10$ZlsUgEo2MOm0RYxwcP55qukrjipEXYNKyyRfdIKkOEv7RpuXEPhxK'),
    ('D005', '張雅筑醫師', '身心科', '焦慮、失眠、情緒調適', '$2a$10$XsgY9Cmk7PqJ2pve2k4xwuTnV/hakC6LOGJqicQyjH.wDiM7PQhWa');

INSERT INTO patients (chart_no, name, gender, birth_date, phone) VALUES
    ('TEST00001', '測試病患甲', '男', '1985-03-15', '0912-345-678'),
    ('TEST00002', '王小明',     '男', '1990-07-22', '0923-456-789'),
    ('TEST00003', '李小華',     '女', '1988-11-30', '0934-567-890');

INSERT INTO appointments (appt_id, chart_no, doctor_id, appt_date, time_slot, status) VALUES
    (1, 'TEST00001', 'D001', '2026-05-01', 'AM', 'BOOKED'),
    (2, 'TEST00002', 'D002', '2026-05-01', 'AM', 'BOOKED'),
    (3, 'TEST00003', 'D003', '2026-05-02', 'PM', 'BOOKED');
