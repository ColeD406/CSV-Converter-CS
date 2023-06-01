# ClockShark Export Converter

## Overview

The ClockShark Export Converter is a powerful application designed to streamline the process of using ClockShark timecard export data with accounting software. It saves users valuable time when running payroll by automating the conversion and integration of data.

**This is an internal tool and is not outwardly facing the internet**

## Features

1. Mass Export CSV Import: The application allows users to import mass export CSV files generated from ClockShark effortlessly. This ensures a seamless integration of timecard data into the system.

2. Custom Column Selection: Users can choose which columns from the imported CSV file they want to include in the new export. Additionally, they have the flexibility to specify new names for these columns, enabling personalized data organization.

3. Time Structure Options: The application provides users with the ability to choose between the original structure for regular time, overtime, and double time, or combine them into a single pay component column. This empowers users to tailor the exported data based on their accounting software's requirements.

4. Email Integration: The ClockShark Export Converter seamlessly integrates with email functionality. After the conversion process, the application sends an email with the modified CSV file attached. The email recipient is automatically retrieved from the saved email within the MongoDB database, enhancing convenience and efficiency.

5. Once saved, the fields specified within the React page are mapped to the specified columns in the new CSV uploaded to the site, allowing for a saved template that accurately delivers a new csv conversion based upon the user's original spceifications when setting the export file up.

## Benefits

- Time Saving: By automating the conversion and integration process, the application significantly reduces the manual effort required to use ClockShark timecard export data with accounting software. Users can now focus on other critical tasks, saving valuable time during payroll processing.

- Enhanced Accuracy: With the ability to customize column names and select specific columns for export, users can ensure that the data is accurately mapped to their accounting software. This reduces the risk of errors and improves overall data integrity.

- Improved Integration: The ClockShark Export Converter provides seamless integration with email functionality. Users can easily share the converted data with the appropriate recipients, ensuring efficient communication and collaboration within the organization.

## License

The ClockShark Export Converter is licensed under the [MIT License](https://opensource.org/licenses/MIT). Please refer to the LICENSE file for more information.

Note: This readme file is a template and should be modified according to your application's specific details and requirements.
