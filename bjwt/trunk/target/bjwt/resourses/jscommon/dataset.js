function Dataset(){
	this.list = new Object();
	this.index=0;
	this.current = null;
	this.size = 0;
}
Dataset.prototype.getCurrent= function(){
	return this.list[this.current];
};

Dataset.prototype.setCurrent= function(cur){
	if(this.current!=cur){
		this.beforeCurrentChanged(this.current,cur);
		var oldCur = this.current;
		this.current = cur;
		this.afterCurrentChanged(cur,oldCur);
	}
};

Dataset.prototype.beforeCurrentChanged= function(cur,newCur){

};
Dataset.prototype.afterCurrentChanged= function(cur,oldCur){

};

Dataset.prototype.add = function(obj){
	this.list[this.index]=obj;
	//this.current = this.index;
	this.size++;
	this.index++;
	return this.index-1;
};


Dataset.prototype.remove = function(index){
	if(index&&index!=this.current){
		delete this.list[index];
		this.size--;
	}else{
		var cur = this.current;
		delete this.list[cur];
		for(cur;cur>=-1;cur--){
			if(this.list[cur]){
				break;
			}
		}
		if(cur!=-1){
			this.current = cur;
			return;
		}
		for(cur = this.current;cur<=this.index+1;cur++){
			if(this.list[cur]){
				break;
			}
		}
		if(cur!=(this.index+1)){
			this.current = cur;
			return;
		}
		this.current = null;
	}
};