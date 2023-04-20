import pandas as pd

def read_csv(file_name):
    return pd.read_csv(file_name)

def filter_data(df, month, year):
    df['Timestamp'] = pd.to_datetime(df['Timestamp'])
    df = df[(df['Timestamp'].dt.month == month) & (df['Timestamp'].dt.year == year)]
    return df

def get_extreme_records(df, column):
    min_records = df[df[column] == df[column].min()]
    max_records = df[df[column] == df[column].max()]
    return min_records, max_records

def main():
    file_name = "SingaporeWeather.csv" # Replace with your file name

    # Ask user for the desired month and year
    year = int(input("Please enter the year (e.g., 2002): "))
    month = int(input("Please enter the month (1-12): "))

    df = read_csv(file_name)
    filtered_data = filter_data(df, month, year)

    min_temperature_records, max_temperature_records = get_extreme_records(filtered_data, 'Temperature')
    min_humidity_records, max_humidity_records = get_extreme_records(filtered_data, 'Humidity')

    print("Minimum Temperature Records:")
    print(min_temperature_records)
    print("\nMaximum Temperature Records:")
    print(max_temperature_records)
    print("\nMinimum Humidity Records:")
    print(min_humidity_records)
    print("\nMaximum Humidity Records:")
    print(max_humidity_records)

if __name__ == "__main__":
    main()