export default function find(options, search, return_all=false, coverage_multiplier=0.02975){
  search = trim(search.toLowerCase());

  var parts = [];
  for (var size = 1; size < search.length+1; size++) {
    for (var i = 0; i < search.length; i++) {
      var part = search.substring(i, i+size);
      if (!parts.includes(part)){
        parts.push(part);
      }
    }
  }

  parts.sort(compareLength);

  var words = search.split(" ");

  var max_coverage = 1 - search.length * coverage_multiplier;

  var results = [];

  for (var i in options){
    var option = options[i];

    if (typeof option == "string"){
      option = {key: option};
    }

    var key = trim(option.key.toLowerCase());
    var tags = "tags" in option ? option["tags"] : [];

    var best = "";
    for (var j in parts){
      if (key.includes(parts[j])){
        best = parts[j];
        break;
      }
    }
    var coverage = best.length / search.length;

    var match = key == search ? 1 : 0;
    var word_matches = [];
    var tag_match = false;
    var tag_occurence = false;
    var starts_with = key.startsWith(search) ? 2 : 0;
    var starts_with_word = 0;
    var starts_with_key = search.startsWith(key) ? 1 : 0;
    var key_in_search = search.includes(key) ? 1 : 0;
    var search_in_key = key.includes(search) ? 2 : 0;

    for (var j in words){
      var word = words[j];

      for (var k in key.split(" ")){
        var word1 = key.split(" ")[k];

        if (word == word1 || word1.startsWith(word) || word1.endsWith(word) || word.startsWith(word1) || word.endsWith(word1)){
          if (key.startsWith(word1)){
            starts_with_word = 2;
          }
          if (word_matches.indexOf(word1) == -1) { // word1 not present in word_matches
            word_matches.push(word1);
          }
        }
      }

      for (var k in tags){
        var tag = tags[k];
        if (search == tag) {
          tag_match = true;
        } else if (tag.includes(word)) {
          tag_occurence = true;
        }
      }
    }

    var possible_accuracy = 1 + 2 + 1 + 1 + 2 + 2 + words.length;
    var accuracy = (match + starts_with + starts_with_word + starts_with_key + key_in_search + search_in_key + word_matches.length) / possible_accuracy;

    if (coverage < max_coverage && !tag_match && !tag_occurence){
      continue;
    }

    var cat = 7;
    if (match == 1){
      cat = 0;
    } else if (search_in_key == 2){
      cat = 1;
    } else if (starts_with == 2){
      cat = 2;
    } else if (tag_match){
      cat = 3;
    } else if (starts_with_word == 2){
      cat = 4;
    } else if (key_in_search == 1){
      cat = 5;
    } else if (starts_with_key == 1){
      cat = 6;
    } else if (tag_occurence) {
      cat = 7;
    } else {
      continue;
    }

    option["coverage"] = coverage;
    option["accuracy"] = accuracy;
    option["cat"] = cat;
    option["match"] = match == 1;

    if (!return_all && match == 1){
      return [option];
    }

    results.push(option);
  }

  results.sort(compareAccuracy);
  results.sort(compareCat);

  return results;
}

function trim(s){
  return ( s || '' ).replace( /^\s+|\s+$/g, '' );
}

function compareLength(a,b){
  if (a.length > b.length){
    return -1;
  } else if (a.length < b.length){
    return 1;
  }
  return 0;
}

function compareCat(a,b){
  if (a["cat"] > b["cat"]){
    return 1;
  } else if (a["cat"] < b["cat"]){
    return -1;
  }
  return 0;
}

function compareAccuracy(a,b){
  if (a["accuracy"] > b["accuracy"]){
    return -1;
  } else if (a["accuracy"] < b["accuracy"]){
    return 1;
  }
  return 0;
}
