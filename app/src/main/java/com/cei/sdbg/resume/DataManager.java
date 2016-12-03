package com.cei.sdbg.resume;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

/**
 * Created by joy on 2016/10/1.
 */

public class DataManager {
    public static final String TAG = "DataManager";
    public static final String DATE_FORMAT = "yyyy/MM/dd";
    public static final String FILE_DIRECTORY = "/sdcard";
    public static final String FILE_NAME = "SDBGResume.csv";
    public static final String DELIMINATOR = ",";
    private String mEvent;
    private String mFillingTime;
    private String mName;
    private String mIdNumber;
    private String mGender;
    private String mBirthday;
    private String mMilitaryStatus;
    private String mPhoneNumber;
    private String mEmail;
    private String mCityAddress;
    private String mRegionAddress;
    private String mHighestEducation;
    private String mSchool;
    private String mMajor;
    private String mSchoolStart;
    private String mSchoolEnd;
    private String mIsGraduateThisYear;
    private String mSkill;
    private boolean mNoLanguageCertificate;
    private String mLanguageCertificateType1;
    private String mLanguageCertificate1;
    private String mLanguageCertificateType2;
    private String mLanguageCertificate2;
    private String mFuturePlan;
    private String mJobType;
    private String mJobFunction;
    private String mAvailableDate;
    private static DataManager mInstance;


    DataManager() {
        mEvent = "";
        mFillingTime = "";
        mName = "";
        mIdNumber = "";
        mGender = "";
        mBirthday = "";
        mMilitaryStatus = "";
        mPhoneNumber = "";
        mEmail = "";
        mCityAddress = "";
        mRegionAddress = "";
        mHighestEducation = "";
        mSchool = "";
        mMajor = "";
        mSchoolStart = "";
        mSchoolEnd = "";
        mIsGraduateThisYear = "";
        mSkill = "";
        mNoLanguageCertificate = true;
        mLanguageCertificateType1 = "";
        mLanguageCertificate1 = "";
        mLanguageCertificateType2 = "";
        mLanguageCertificate2 = "";
        mFuturePlan = "";
        mJobType = "";
        mJobFunction = "";
        mAvailableDate = "";

        mInstance = null;
    }

    static DataManager getmInstance() {
        if (mInstance == null) {
            mInstance = new DataManager();
        }

        return mInstance;
    }

    void resetDataManager() {
        mInstance = null;
    }

    String getEvent() {
        return mEvent;
    }

    void setEvent(String event) {
        mEvent = event;
    }

    String getFillingTime() {
        return mFillingTime;
    }

    void setFillingTime() {
        Calendar calendar = Calendar.getInstance();
        mFillingTime = DateFormat.format(DATE_FORMAT, calendar.getTime()).toString();
    }

    void setFillingTime(String fillingTime) {
        mFillingTime = fillingTime;
    }

    String getName() {
        return mName;
    }

    void setName(String name) {
        mName = name;
    }

    String getIdNumber() {
        return mIdNumber;
    }

    void setIdNumber(String idNumber) {
        mIdNumber = idNumber;
    }

    String getGender() {
        return mGender;
    }

    void setGender(String gender) {
        mGender = gender;
    }

    String getBirthday() {
        return mBirthday;
    }

    void setmBirthday(String birthday) {
        mBirthday = birthday;
    }

    String getMilitaryStatus() {
        return mMilitaryStatus;
    }

    void setMilitaryStatus(String militaryStatus) {
        mMilitaryStatus = militaryStatus;
    }

    String getPhoneNumber() {
        return mPhoneNumber;
    }

    void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    String getEmail() {
        return mEmail;
    }

    void setEmail(String email) {
        mEmail  = email;
    }

    String getCityAddress() {
        return mCityAddress;
    }

    void setCityAddress(String address) {
        mCityAddress = address;
    }

    String getRegionAddress() {
        return mRegionAddress;
    }

    void setRegionAddress(String address) {
        mRegionAddress = address;
    }

    String getHighestEducation() {
        return mHighestEducation;
    }

    void setHighestEducation(String highestEducation) {
        mHighestEducation = highestEducation;
    }

    String getSchool() {
        return mSchool;
    }

    void setSchool(String school) {
        mSchool = school;
    }

    String getMajor() {
        return mMajor;
    }

    void setMajor(String major) {
        mMajor = major;
    }

    String getSchoolStart() {
        return mSchoolStart;
    }

    void setSchoolStart(String schoolStart) {
        mSchoolStart = schoolStart;
    }

    String getSchoolEnd() {
        return mSchoolEnd;
    }

    void setSchoolEnd(String schoolEnd) {
        mSchoolEnd = schoolEnd;
    }

    String isGraduateThisYear() {
        return mIsGraduateThisYear;
    }

    void setIsGraduateThisYear(String isGraduateThisYear) {
        mIsGraduateThisYear = isGraduateThisYear;
    }

    String getSkill() {
        return mSkill;
    }

    void setSkill(String skill) {
        mSkill = skill;
    }

    boolean isNoLanguageCertificate() {
        return mNoLanguageCertificate;
    }

    void setNoLanguageCertificate(boolean noLanguageCertificate) {
        mNoLanguageCertificate = noLanguageCertificate;
    }

    String getLanguageCertificateType1() {
        return mLanguageCertificateType1;
    }

    void setLanguageCertificateType1(String languageCertificateType1) {
        mLanguageCertificateType1 = languageCertificateType1;
    }

    String getLanguageCertificate1() {
        return mLanguageCertificate1;
    }

    void setLanguageCertificate1(String languageCertificate1) {
        mLanguageCertificate1 = languageCertificate1;
    }

    String getLanguageCertificateType2() {
        return mLanguageCertificateType2;
    }

    void setLanguageCertificateTyp2(String languageCertificateTyp2) {
        mLanguageCertificateType2 = languageCertificateTyp2;
    }

    String getLanguageCertificate2() {
        return mLanguageCertificate2;
    }

    void setLanguageCertificate2(String languageCertificate2) {
        mLanguageCertificate2 = languageCertificate2;
    }

    String getFuturePlan() {
        return mFuturePlan;
    }

    void setFuturePlan(String futurePlan) {
        mFuturePlan = futurePlan;
    }

    String getJobType() {
        return mJobType;
    }

    void setJobtype(String jobtype) {
        mJobType = jobtype;
    }

    String getJobFunction() {
        return mJobFunction;
    }

    void setJobFunction(String jobFunction) {
        mJobFunction = jobFunction;
    }

    String getAvailableDate() {
        return mAvailableDate;
    }

    void setAvailableDate(String availableDate) {
        mAvailableDate = availableDate;
    }

    String getHeader(Context context) {
        String header = "";
        String[] titles = context.getResources().getStringArray(R.array.array_title);

        for (String title : titles) {
            if (header.isEmpty()) {
                header = title;
            } else {
                header += (DELIMINATOR + title);
            }
        }

        return header;
    }

    String getDataString(Context context) {
        String dataString = "";

        dataString =
                "\"" + mEvent + "\"" + DELIMINATOR +
                "\"" + mFillingTime + "\"" + DELIMINATOR +
                "\"" + mName + "\"" + DELIMINATOR +
                "\"=\"\"" + mIdNumber + "\"\"\"" + DELIMINATOR +
                "\"" + mGender + "\"" + DELIMINATOR +
                "\"" + mBirthday + "\"" + DELIMINATOR +
                "\"" + mMilitaryStatus + "\"" + DELIMINATOR +
                "\"=\"\"" + mPhoneNumber + "\"\"\"" + DELIMINATOR +
                "\"" + mEmail + "\"" + DELIMINATOR +
                "\"" + mCityAddress + mRegionAddress + "\"" + DELIMINATOR +
                "\"" + mHighestEducation + "\"" + DELIMINATOR +
                "\"" + mSchool + "\"" + DELIMINATOR +
                "\"" + mMajor + "\"" + DELIMINATOR +
                "\"" + mSchoolStart + "\"" + DELIMINATOR +
                "\"" + mSchoolEnd + "\"" + DELIMINATOR +
                "\"" + mIsGraduateThisYear + "\"" + DELIMINATOR +
                "\"" +mSkill + "\"" + DELIMINATOR +
                "\"" + (mNoLanguageCertificate ?
                        context.getString(R.string.yes) : context.getString(R.string.no)) +
                        "\"" + DELIMINATOR +
                "\"" + mLanguageCertificateType1 + "\"" + DELIMINATOR +
                "\"" + mLanguageCertificate1 + "\"" + DELIMINATOR +
                "\"" + mLanguageCertificateType2 + "\"" + DELIMINATOR +
                "\"" + mLanguageCertificate2 + "\"" + DELIMINATOR +
                "\"" + mFuturePlan + "\"" + DELIMINATOR +
                "\"" + mJobType + "\"" + DELIMINATOR +
                "\"" + mJobFunction + "\"" + DELIMINATOR +
                "\"" + mAvailableDate + "\"";

        return dataString;
    }

    void saveToFile(Context context) {
        File file = new File(FILE_DIRECTORY, FILE_NAME);

        file.setReadable(true, false);
        file.setWritable(true, false);

        try {
            if (!file.exists()) {
                file.createNewFile();

                // Write the header if the file is newly created.
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(getHeader(context));
                bw.newLine();
                bw.flush();
                bw.close();
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(getDataString(context));
            bw.newLine();
            bw.flush();
            bw.close();
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }





}
