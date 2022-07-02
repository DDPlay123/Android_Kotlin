package com.tutorial.youtubemediaplayer.JavaScript

class Youtube {
    fun buildHtml(youtubeId: String): String {
        return """
        <!DOCTYPE html>
            <html>
                <head>
                    <meta charset="utf-8" />
                    <style>
			            .overlay {
				            position: fixed;
				            width: 100%;
				            height: 100%;
				            left: 0;
				            top: 0;
				            background: rgba(51,51,51,0.7);
				            z-index: 10;}
                    </style>
                </head>
                <body>
                    <!-- 1. The <iframe> (and video player) will replace this <div> tag. -->
                    <div class="overlay", id="player"></div>
                    <script>
                        // 2. This code loads the IFrame Player API code asynchronously.
			            var tag = document.createElement('script');
                        tag.src = "https://www.youtube.com/iframe_api";
			            var firstScriptTag = document.getElementsByTagName('script')[0];
			            firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
                        // 3. This function creates an <iframe> (and YouTube player)
			            //    after the API code downloads.
                        var player;
			            function onYouTubeIframeAPIReady() {
				            player = new YT.Player('player', {
					            height: '100%',
					            width: '100%',
					            playerVars: {
						            autoplay: 1,// 自動播放影片
      					            controls: 0,// 顯示播放器
      					            showinfo: 0,// 隱藏影片標題
      					            modestbranding: 1,// 隱藏YouTube Logo
      					            fs: 0,// 隱藏全螢幕按鈕
                                    rel: 0, //不顯示相關視頻
     					            cc_load_policty: 0,// 隱藏字幕
      					            iv_load_policy: 3,// 隱藏影片註解
      					            autohide: 0,// 影片播放時，隱藏影片控制列
						            mute : 0//靜音
                                },
          			            videoId: "$youtubeId",
					            events: {
						            'onReady': onPlayerReady,
						            'onStateChange': onPlayerStateChange
					            }
				            });
			            }
                        // 4. The API will call this function when the video player is ready.
			            function onPlayerReady(event) {;
				            event.target.playVideo();
			            }
                        // 5. The API calls this function when the player's state changes.
			            //    The function indicates that when playing a video (state=1),
			            //    the player should play for six seconds and then stop.
			            var refreshInterval;
                        function onPlayerStateChange(event) {
				            if (player.getPlayerState() === 0) {
                                android.playerIcon(2)
                                clearInterval(refreshInterval);
                                refreshInterval = null;
                                seekTo(0);
                                pauseVideo();
				            }
                            if (player.getPlayerState() === 1) {
                                android.playerIcon(1)
                                refreshInterval = setInterval('currentTime()', 500);
                            }
                            if (player.getPlayerState() === 2) {
                                android.playerIcon(0)
                                clearInterval(refreshInterval);
                                refreshInterval = null;
                            }
                            if (player.getPlayerState() === 3) {
                                android.playerIcon(0)
                                clearInterval(refreshInterval);
                                refreshInterval = null;
                            }
			            }
                        function currentTime() {
                            android.getCurrentTime(player.getCurrentTime());
                        }
                        function seekTo(time) {
                            player.seekTo(time);
                        }
                        function stopVideo() {
				            player.stopVideo();
			            }
                        function pauseVideo() {
				            player.pauseVideo();
			            }
                        function playVideo() {
                            android.getCurrentTime(player.getCurrentTime());
				            player.playVideo();
			            } 
                    </script>
	            </body>
            </html>
        """.trimIndent()
    }
}