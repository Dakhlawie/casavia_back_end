<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Email Verification</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            max-width: 600px;
            margin: 20px auto;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        h1 {
            color: #333;
        }
        p {
            color: #555;
        }
        .verification-code {
            background-color: #f0f0f0;
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 10px;
            font-size: 18px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">

    <img src="https://drive.google.com/file/d/1RFsQLMCQ2GJdYDiu28OjdA6O6VEfn4P8/view" alt="Email Verification Image" style="width: 100%; max-width: 100%; height: auto;">
    <p>Hey there!</p>
    <p>Please use the verification code below to verify your email address:</p>
    <div class="verification-code">${verificationCode}</div>
    <p>Thank you.</p>
</div>
</body>
</html>
