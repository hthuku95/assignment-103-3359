package com.noteapp.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

/**
 * Utility class for date formatting and manipulation operations
 * Provides methods for formatting, parsing, and comparing dates
 */
public class DateUtils {
    
    private static final Logger logger = Logger.getLogger(DateUtils.class.getName());
    
    // Common date format patterns
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DISPLAY_DATE_TIME_FORMAT = "MMM dd, yyyy hh:mm a";
    public static final String SHORT_DATE_FORMAT = "MM/dd/yyyy";
    public static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    
    // Formatters
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
    private static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern(DISPLAY_DATE_TIME_FORMAT);
    private static final DateTimeFormatter SHORT_FORMATTER = DateTimeFormatter.ofPattern(SHORT_DATE_FORMAT);
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern(ISO_DATE_TIME_FORMAT);
    
    private DateUtils() {
        // Private constructor to prevent instantiation
    }
    
    /**
     * Gets the current date and time
     * @return Current LocalDateTime
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
    
    /**
     * Formats a LocalDateTime using the default format
     * @param dateTime The date time to format
     * @return Formatted date string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        try {
            return dateTime.format(DEFAULT_FORMATTER);
        } catch (Exception e) {
            logger.warning("Error formatting date: " + e.getMessage());
            return dateTime.toString();
        }
    }
    
    /**
     * Formats a LocalDateTime for display purposes
     * @param dateTime The date time to format
     * @return User-friendly formatted date string
     */
    public static String formatForDisplay(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "Unknown";
        }
        try {
            return dateTime.format(DISPLAY_FORMATTER);
        } catch (Exception e) {
            logger.warning("Error formatting date for display: " + e.getMessage());
            return formatDateTime(dateTime);
        }
    }
    
    /**
     * Formats a LocalDateTime using a short date format
     * @param dateTime The date time to format
     * @return Short formatted date string
     */
    public static String formatShortDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        try {
            return dateTime.format(SHORT_FORMATTER);
        } catch (Exception e) {
            logger.warning("Error formatting short date: " + e.getMessage());
            return formatDateTime(dateTime);
        }
    }
    
    /**
     * Formats a LocalDateTime using ISO format
     * @param dateTime The date time to format
     * @return ISO formatted date string
     */
    public static String formatISODateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        try {
            return dateTime.format(ISO_FORMATTER);
        } catch (Exception e) {
            logger.warning("Error formatting ISO date: " + e.getMessage());
            return dateTime.toString();
        }
    }
    
    /**
     * Parses a date string using the default format
     * @param dateString The date string to parse
     * @return Parsed LocalDateTime or null if parsing fails
     */
    public static LocalDateTime parseDateTime(String dateString) {
        if (dateString == null || dateString.