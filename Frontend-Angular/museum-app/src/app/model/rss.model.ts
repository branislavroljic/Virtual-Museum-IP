export class RssData{
   feed : Feed;
   items : Item[];


  constructor(feed: Feed, items: Item[]) {
    this.feed = feed
    this.items = items
  }

}

export class Item{
    title : string;
    pubDate : string;
    link : string;
    author : string;
    thumbnail : string;
    description : string;
    content : string;
    enclosure : Enclosure;
    categories : any[];


  constructor(
    title: string, 
    pubDate: string, 
    link: string, 
    author: string, 
    thumbnail: string, 
    description: string, 
    content: string, 
    enclosure: Enclosure, 
    categories: any[]
) {
    this.title = title
    this.pubDate = pubDate
    this.link = link
    this.author = author
    this.thumbnail = thumbnail
    this.description = description
    this.content = content
    this.enclosure = enclosure
    this.categories = categories
  }


}

class Enclosure{
    link : string;
    type : string;

  constructor(link: string, type: string) {
    this.link = link
    this.type = type
  }

}


class Feed{
    title : string;
    link : string;
    author : string;
    description : string;
    image : string;


  constructor(
    title: string, 
    link: string, 
    author: string, 
    description: string, 
    image: string
) {
    this.title = title
    this.link = link
    this.author = author
    this.description = description
    this.image = image
  }

}