var txService = {
    // 전체 Transaction 조회
    findAll: function(callback) {
        $.get(API_BASE_URL + '/api/tx', function(data) {
            callback(data);
        });
    },
    //최근 10개의 Transaction 조회
    find10: function(callback) {
        $.get(API_BASE_URL + '/api/tx10', function(data) {
            callback(data);
        });
    },
     //특정 hash의 Transaction 조회
     find: function(hash, callback) {
        $.get(API_BASE_URL + "/api/txfind/" + hash, callback);
    },
     //address로 Transaction 조회
     findaddress: function(address, callback) {
        $.get(API_BASE_URL + "/api/txaddress/" + address, callback);
    }
}