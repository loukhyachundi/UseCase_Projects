DROP TABLE IF EXISTS treatments;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS patients;

CREATE TABLE patients (
    patient_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10) NOT NULL
);

CREATE TABLE doctors (
    doctor_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL
);

CREATE TABLE appointments (
    appointment_id INT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)
);

CREATE TABLE treatments (
    treatment_id INT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    treatment_date DATE NOT NULL,
    diagnosis VARCHAR(150) NOT NULL,
    cost DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)
);

INSERT INTO patients VALUES (1, 'Aarav Sharma', 34, 'Male'), (2, 'Diya Patel', 28, 'Female');

INSERT INTO doctors VALUES (1, 'Dr. Priya Nair', 'Cardiology'), (2, 'Dr. Vikram Rao', 'Orthopedics');

INSERT INTO appointments VALUES
    (1, 1, 1, '2026-01-05'),
    (2, 2, 2, '2026-01-07'),
    (3, 1, 1, '2026-03-14');

INSERT INTO treatments VALUES
    (1, 1, 1, '2026-01-05', 'Hypertension', 2500.00),
    (2, 2, 2, '2026-01-07', 'Viral Fever', 800.00),
    (3, 1, 1, '2026-03-14', 'Follow-up Cardiac Check', 1800.00);

SELECT d.doctor_id, d.name AS doctor_name, COUNT(a.appointment_id) AS consultation_count
FROM doctors d LEFT JOIN appointments a ON a.doctor_id = d.doctor_id
GROUP BY d.doctor_id, d.name ORDER BY consultation_count DESC;

SELECT EXTRACT(YEAR FROM treatment_date) AS revenue_year, EXTRACT(MONTH FROM treatment_date) AS revenue_month, SUM(cost) AS total_revenue
FROM treatments GROUP BY EXTRACT(YEAR FROM treatment_date), EXTRACT(MONTH FROM treatment_date)
ORDER BY revenue_year, revenue_month;

SELECT diagnosis, COUNT(*) AS diagnosis_count
FROM treatments GROUP BY diagnosis ORDER BY diagnosis_count DESC;

SELECT p.patient_id, p.name AS patient_name, COUNT(a.appointment_id) AS visit_count
FROM patients p LEFT JOIN appointments a ON a.patient_id = p.patient_id
GROUP BY p.patient_id, p.name ORDER BY visit_count DESC;

SELECT d.doctor_id, d.name AS doctor_name, COALESCE(a.total_appointments, 0) AS total_appointments,
       COALESCE(t.total_treatments, 0) AS total_treatments, COALESCE(t.total_revenue, 0) AS total_revenue,
       COALESCE(t.avg_treatment_cost, 0) AS avg_treatment_cost
FROM doctors d
LEFT JOIN (SELECT doctor_id, COUNT(*) AS total_appointments FROM appointments GROUP BY doctor_id) a
    ON a.doctor_id = d.doctor_id
LEFT JOIN (SELECT doctor_id, COUNT(*) AS total_treatments, SUM(cost) AS total_revenue, ROUND(AVG(cost), 2) AS avg_treatment_cost
           FROM treatments GROUP BY doctor_id) t
    ON t.doctor_id = d.doctor_id
ORDER BY total_revenue DESC, total_appointments DESC;
