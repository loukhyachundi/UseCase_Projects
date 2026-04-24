# HospiManager
A simple Hospital Management SQL project that models core healthcare operations:
- Patient records
- Doctor records
- Appointments
- Treatments and billing

This repository currently contains a single SQL script that creates tables, inserts sample data, and runs useful reporting queries.

## Project Structure
- `hospital_management.sql` : Complete database setup + sample analytics

## Database Schema
The script creates 4 related tables:

1. **patients**
   - `patient_id` (Primary Key)
   - `name`
   - `age`
   - `gender`

2. **doctors**
   - `doctor_id` (Primary Key)
   - `name`
   - `specialization`

3. **appointments**
   - `appointment_id` (Primary Key)
   - `patient_id` (Foreign Key -> patients)
   - `doctor_id` (Foreign Key -> doctors)
   - `appointment_date`

4. **treatments**
   - `treatment_id` (Primary Key)
   - `patient_id` (Foreign Key -> patients)
   - `doctor_id` (Foreign Key -> doctors)
   - `treatment_date`
   - `diagnosis`
   - `cost`

## What the Script Includes
- Safe reset of existing tables (`DROP TABLE IF EXISTS`)
- Table creation with referential integrity
- Sample inserts for:
  - 2 patients
  - 2 doctors
  - 3 appointments
  - 3 treatment records
- Reporting queries for:
  - Consultation count per doctor
  - Monthly treatment revenue
  - Diagnosis frequency
  - Patient visit count
  - Doctor-wise consolidated performance summary

## How to Run
1. Open your SQL client (MySQL/PostgreSQL-compatible syntax is used).
2. Create or select a database, for example:

```sql
CREATE DATABASE hospi_manager;
USE hospi_manager;
```

3. Run the script:
```sql
SOURCE hospital_management.sql;
```

If your SQL tool does not support `SOURCE`, copy and execute the file contents directly.

## Example Insights You Can Get
- Which doctor handled the most consultations
- Monthly revenue trend from treatments
- Most common diagnoses
- Which patients visit most frequently
- Doctor-level treatment volume and average treatment cost

## Future Improvements
- Add indexes for faster reporting
- Add constraints/checks for valid age and gender values
- Add appointment status (scheduled/completed/cancelled)
- Add departments and room allocation modules
- Add views/stored procedures for dashboard-style reporting


