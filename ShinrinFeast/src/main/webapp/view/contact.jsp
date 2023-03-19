<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<html lang="UTF-8">
	
<head>
    <meta charset="utf-8">
    <title>
        森林の宴 - CONTACT
    </title>
    <meta name="description" content="キャンプを支配する。">

    <!--css-->
    <link rel="stylesheet" href="https://unpkg.com/ress/dist/ress.min.css">
    <link href="https://fonts.googleapis.com/css?family=Philosopher" rel="stylesheet">
    <link href="style.css" rel="stylesheet">
    <link rel="icon" type="image/png" href="images/camp_tent_illust_4022.png">
</head>

<header class="page-header wrapper">
    <nav>
        <ul class="main-nav">
            <li><a href="index.html">Top</a></li>
            <li><a href="news.html">News</a></li>
            <li><a href="menu.html">Menu</a></li>
            <li><a href="contact.jsp">Contact</a></li>
            <li><a href="Login.jsp">Login</a></li>
        </ul>
    </nav>
</header>



<body>
    <div id="contact" class="big-bg">

            <div class="wrapper">
               <h2 class="page-title">Contact</h2>
                   <form action="<%=request.getContextPath()%>/SurveyByQuestionnaire" method="post">
                    <div>
                        <label for="name">お名前</label>
                        <input type="text" id="NAME" name="NAME">
                    </div>
                    
                    <div>
                        <label for="email">メールアドレス</label>
                        <input type="email" id="EMAIL" name="EMAIL">
                    </div>
            
                    <div>
                        <label for="message">メッセージ</label>
                        <textarea id="MESSAGE" name="MESSAGE"></textarea>
                    </div>
            
                    <input type="submit" class="button" value="送信" id="ID_SUBMIT">
                </form>
                <script type="text/javascript" src="Survey.js"></script> 
            </div>
    </div>

    <section id="location">
        <div class="wrapper">
            <div class="location-info">
                <h3 class="subtitle">森林の宴</h3>
                <p>
                    長野県長野市<br>
                    森林町森林の里9-9<br>
                    026-bbbb-aaaa<br>
                    9:00～18:00<br>
                    年中無休（年末年始除く）<br>
                </p>
            </div><!-- location-info-->
            <div class="location-map">
                Googleマップ
                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3201.4097525713773!2d138.23804361474126!3d36.64059198436855!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x601d8f57fbb20bad%3A0x3d3e2b067abcb1c7!2z6ZW36YeO5biC44Kq44Oq44Oz44OU44OD44Kv6KiY5b-144Ki44Oq44O844OKKOOCqOODoOOCpuOCp-ODvOODlu-8iQ!5e0!3m2!1sja!2sjp!4v1677564836324!5m2!1sja!2sjp" width="800" height="400" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
            </div><!-- /.location-map -->
        </div><!-- /.wrapper -->
    </section><!-- /"location"-->

    <footer>
        <div class ="wrapper">
            <p><small>&copy;2023 WadaTetsu</small></p>
        </div>
    </footer>

</body>
</html>
