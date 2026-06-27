# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# signingConfig buildTypes.release.signingConfig configuration property.

-keep class com.drop.** { *; }
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Preserve line numbers for debugging stack traces.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
