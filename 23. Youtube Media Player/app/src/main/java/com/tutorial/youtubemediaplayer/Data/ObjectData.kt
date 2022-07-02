package com.tutorial.youtubemediaplayer.Data

class ObjectData {
    lateinit var result: Result
    class Result {
        var viewer = 0 // 觀看次數
        var mainEditor = "" // 主編名稱

        lateinit var videoInfo: VideoInfo
        class VideoInfo {
            var videourl = "" // 影片id
            var title = "" // 標題
            var thumbnails = "" // 預覽圖連結
            var duration = 0 // 影片長度

            lateinit var captionResult: CaptionResult
            class CaptionResult {
                lateinit var results: Array<Results> // 0->result[0] 為主字幕, result[1] 為輔字幕 空值為沒有輔助字幕
                class Results {
                    lateinit var captions: Array<Captions>
                    class Captions {
                        var miniSecond = 0 // 時間(秒)
                        var content = "" // 內容
                    }
                }
            }
        }
    }
}