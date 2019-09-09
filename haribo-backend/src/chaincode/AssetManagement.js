const shim = require('fabric-shim');
const util = require('util');

var Chaincode = class {

    async Init(stub){
        console.info('Instantiated completed');
        return shim.success();
    }

    async Invoke(stub){
        let ret = stub.getFunctionAndParameters();
        console.info(ret);

        let method = this[ret.fcn];
     
        if(!method){
            console.log('Method name [' + ret.fcn + '] is not found');
            return shim.success();
        }

        try{
            let payload = await method(stub, ret.params);
            return shim.success(payload);
        }catch(err){
            console.log(err);
            return shim.error(err);
        }
    }

    // args[0]: assetID
    // args[1]: owner
    async registerAsset(stub, args){

        if(args.length != 2){
            throw new Error('Incorrect number of arguments. Expecting 2, but received ' + args.length);
        }

        var assetInfo = {
            assetID: args[0],
            owner: args[1],
            createdAt: 'FALSE',
            expiredAt: 'FALSE'
        };

        let compositeKey = stub.createCompositeKey("Asset.", [args[0]]);
    
        await stub.putState(compositeKey, Buffer.from(JSON.stringify(assetInfo)));
    
        console.info('Asset is registered');
        console.info(assetInfo);
        
    }

   //args[0]: assetID
    async confirmTimestamp(stub, args){

        if(args.length != 1){
            throw new Error('Incorrect number of arguments. Expecting assetID as an argument');
        }

        let searchKey = stub.createCompositeKey("Asset.", [args[0]]);
        let asset = await stub.getState(searchKey);
        let assetInfo = JSON.parse(asset);

        let txTimestamp = stub.getTxTimestamp();

        let tsSec = txTimestamp.seconds;
        let tsSecValue = tsSec.low;
        
        let dataTimeObj = new Date(tsSecValue*1000);

        var timestampString = dataTimeObj.toISOString().replace(/T|Z|\.\d{3}/g, ' ').trim();
    
        console.info(timestampString);

        assetInfo.createdAt = timestampString;
        await stub.putState(searchKey, Buffer.from(JSON.stringify(assetInfo)));

    }

    //args[0] assetID
    async getAssetHistory(stub, args){

        console.info('======== GET ASSET HISTORY ========');

        if(args.length != 1){
            throw new Error('Incorrect number of arguments. Expecting assetID as an argument');
        }
      
        let searchKey = stub.createCompositeKey("Asset.", [args[0]]);
      
        var historyIter = await stub.getHistoryForKey(searchKey);

        let results = [];
        let res = {done : false};
    
        while(!res.done){
            res = await historyIter.next();
    
            try{

                console.info('======= RES.VALUE.VALUE =======');
                console.info(res.value.value);

                if(res && res.value && res.value.value){
                    let val = res.value.value.toString('utf8');
        
                    if(val.length > 0){
                        results.push(JSON.parse(val));
                    }
                }

            }catch(err){
                console.info(err);
            }
    
            if(res && res.done){
                try{
                    historyIter.close();
                }catch(err){
                    console.info(err);
                }
            }
        }    
    
        console.info(results);

        return Buffer.from(JSON.stringify(results));
        
    }

    // args[0]: assetID
    async expireAssetOwnership(stub, args){
        console.info('======== EXPIRE ASSET OWNERSHIP ========');

        let searchKey = stub.createCompositeKey("Asset.", [args[0]]);
        
        let asset = await stub.getState(searchKey);
        let assetInfo = JSON.parse(asset);

        try{
            console.info('assetInfo.assetID   ===== : ', assetInfo.assetID);
            console.info('assetInfo.owner     ===== : ', assetInfo.owner);
            console.info('assetInfo.createdAt ===== : ', assetInfo.createdAt);
            console.info('assetInfo.expiredAt ===== : ', assetInfo.expiredAt);
        }catch(err){
            console.info(err);
        }
        
        let txTimestamp = stub.getTxTimestamp();

        let tsSec = txTimestamp.seconds;
        let tsSecValue = tsSec.low;
        
        let dataTimeObj = new Date(tsSecValue*1000);

        var timestampString = dataTimeObj.toISOString().replace(/T|Z|\.\d{3}/g, ' ').trim();
    
        console.info(timestampString);


        assetInfo.expiredAt = timestampString;

        await stub.putState(searchKey, Buffer.from(JSON.stringify(assetInfo)));
        
    }

    // args[0]: assetID(string)
    // args[1]: new owner(string)
    async updateAssetOwnership(stub, args){
        console.info('======== UPDATE ASSET OWNERSHIP ========');

        if(args.length != 2){
            throw new Error('Incorrect number of arguments. Expecting 2, but received' + args.length);
        }


        let searchKey = stub.createCompositeKey("Asset.", [args[0]]);
        
        let updatedAsset = await stub.getState(searchKey);
        let updatedAssetInfo = JSON.parse(updatedAsset);
    
        try{
            console.info('updatedAssetInfo.assetID   ===== : ', updatedAssetInfo.assetID);
            console.info('updatedAssetInfo.owner     ===== : ', updatedAssetInfo.owner);
            console.info('updatedAssetInfo.createdAt ===== : ', updatedAssetInfo.createdAt);
            console.info('updatedAssetInfo.expiredAt ===== : ', updatedAssetInfo.expiredAt);
        }catch(err){
            console.info(err);
        }
        
        let txTimestamp = stub.getTxTimestamp();
        

        let tsSec = txTimestamp.seconds;
        let tsSecValue = tsSec.low;
        
        let dataTimeObj = new Date(tsSecValue*1000);

        var timestampString = dataTimeObj.toISOString().replace(/T|Z|\.\d{3}/g, ' ').trim();
    
        console.info(timestampString);

        updatedAssetInfo.owner = args[1];
        updatedAssetInfo.createdAt = timestampString;
        updatedAssetInfo.expiredAt = 'FALSE'
    
        await stub.putState(searchKey, Buffer.from(JSON.stringify(updatedAssetInfo)));
    }

    //args[0]: assetID
    async query(stub, args){
        console.info('======== QUERY ========');

        let searchKey = stub.createCompositeKey("Asset.", [args[0]]);
        
        let asset = await stub.getState(searchKey);

        console.info(asset);

        return asset;
    }

};  

shim.start(new Chaincode());