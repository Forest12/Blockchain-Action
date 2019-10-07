var auctionService = {
    // 전체 경매 내역 조회
    findAll: function(callback) {
        $.get(API_BASE_URL + '/api/auctions', function(data) {
            callback(data);
        });
    },
    findAllByUser: function(userId, callback) {
        $.get(API_BASE_URL + '/api/auctions/owner/' + userId, function(data) {
            callback(data);
        });
    },
    register: function(data, callback) {
        $.ajax({
            type: "POST",
            url: API_BASE_URL + "/api/auctions",
            data: JSON.stringify(data),
            headers: { 'Content-Type': 'application/json' },
            success: callback
        });
    },
    imgupload: function(img, callback) {
        $.ajax({
            type: "POST",
            timeout: 0,
            processData: false,
            mimeType: "multipart/form-data",
            contentType: false,
            url: 'https://api.imgur.com/3/image',
            data: img,
            headers: { "Authorization": "Client-ID af859ea1e438b12" },
            success: callback
        });
    },
    findById: function(id, callback) {
        $.get(API_BASE_URL + "/api/auctions/" + id, callback);
    },
    // 경매 내역 저장
    saveBid: function(bidder, auctionId, bidPrice, callback) {
        var data = {
            "auctionPartId": bidder,
            "auctionId": auctionId,
            "bidAmount": bidPrice,
            "bidDate": new Date()
        }
        $.ajax({
            type: "PUT",
            url: API_BASE_URL + "/api/auctions/bid",
            data: JSON.stringify(data),
            headers: { 'Content-Type': 'application/json' },
            success: callback
        })
    },
    // 경매 취소
    cancel: function(auctionId, bidderId, callback, whenError) {
        $.ajax({
            type: "DELETE",
            url: API_BASE_URL + "/api/auctions/" + auctionId + "/by/" + bidderId,
            success: callback,
            error: whenError
        });
    },
    // 경매 종료
    close: function(auctionId, bidderId, callback, whenError) {
        $.ajax({
            type: "PUT",
            url: API_BASE_URL + "/api/auctions/" + auctionId + "/by/" + bidderId,
            success: callback,
            error: whenError
        });
    }
}