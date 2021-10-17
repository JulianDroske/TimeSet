## Sync time from custom ntp server on Android

Note: Make sure that you have gained root access and have already installed busybox.


***

# Context

So one of my dev friends is now living in China mainland without any VPN connecting to any "google-accessible" server.

He flashed the phone with an official LineageOS device which uses google as the only NTP server.

It's the problem: "Connected, no internet" would be shown after connected to Wi-Fi, function "Use network-provided time" in Settings is unable to use.


***

# Codes

Part of codes (SNTPClient.java) are copied from a post (i forgot the link...) from stackoverflow, 

It now uses ntp.aliyun.com as the default NTP server, defined at line 241 in SNTPClient.java

Since I cannot find a way to set time properly with pure Java, I use "date" command inside busybox to do this job.

Why i didn't use the built-in "date"? Well, it would kill all user apps (on that device) after setting, which is not suitable for sync.


***

# Usage

Just open it after installation.

If "Program output" shows UTC time, it made a success.

Any crash/error will be shown generally through either "Program output" or Toast.

It is not intended to run for a long time.

***

# Other

Only one thing I cannot understand is that I must use UTC-1 conuntries instead of UTC+0 to adjust the time properly.

I'm not sure if it's a bug, the "date" uses UTC, both "date" and java use CST, but the server returns one hour more when using UTC+0.

***

# About

Author: Julian Droske

IDE (Code Editor & Compiler): AIDE 2015

Host&Tested device: Le X520, Android 8.1.0 Oreo (LineageOS 15.1)
