import { Component, OnInit, Inject, ViewChild, ElementRef, AfterViewChecked, Directive } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef, MatSnackBarConfig, MatSnackBar } from '@angular/material';
import { Message } from '../../models/message/message';
import { ServerService } from '../../services/server/server.service';
import { tap } from 'rxjs/operators';
import { User } from '../../models/user/user';
import { DataService } from '../../services/data/data.service';
import { Item } from '../../models/item/item';
import { AfterViewInit } from '@angular/core/src/metadata/lifecycle_hooks';

// @Directive({
//   selector: 'mat-list',
//   exportAs:'matListdirective'
// })
// class MatList {
// }

@Component({
  selector: 'app-chat-dialog',
  templateUrl: './chat-dialog.component.html',
  styleUrls: ['./chat-dialog.component.css']
})
export class ChatDialogComponent implements OnInit, AfterViewInit {
  // @ViewChild(MatList) private myScrollContainer: MatList;

  item: Item;
  person: User;
  messages: Message[] = [];
  newMessage: string;

  interval: any;

  constructor(public server: ServerService, public dialogRef: MatDialogRef<ChatDialogComponent>, @Inject(MAT_DIALOG_DATA) public input: any, public data: DataService, public snackBar: MatSnackBar) {
    this.item = this.input.item;
    this.person = this.input.person;
  }

  ngOnInit() {
    this.getLatestChat();
    this.interval = setInterval(() => { this.getLatestChat(); }, 1000 * 10);
  }

  ngAfterViewInit() {
    // this.scrollToBottom();
  }

  getLatestChat() {
    this.server.getChat(this.item.id_item, this.person.id_user)
      .pipe(
      tap(messages => {
        this.messages = messages;
        // this.scrollToBottom();
      })
      )
      .subscribe();
  }

  onDone() {
    let config = new MatSnackBarConfig();
    config.extraClasses = ['custom-class'];
    config.duration = 5000;

    let snackBarRef = this.snackBar.open("Etes-vous sûr de vouloir valider la transaction ?", "Oui", config);

    snackBarRef.onAction().subscribe(() => {
      let id_userInterestedBy = (this.person.id_user == this.item.id_user) ? this.data.getUser().id_user : this.person.id_user;

      this.server.updateItemStatus(this.item.id_item, id_userInterestedBy)
        .subscribe(_ => {
          config.duration = 3000;
          this.snackBar.open("La validation a bien été enregistrée !", "", config);

          this.dialogRef.close();
          clearInterval(this.interval);
        });
    });
  }

  onNewMessage() {
    this.server.addChat({
      id_item: this.item.id_item,
      id_sender: this.data.getUser().id_user,
      id_receiver: this.person.id_user,
      message: this.newMessage,
      date: null
    })
      .subscribe(_ => {
        this.newMessage = '';
        this.getLatestChat();
      });
  }

  onBack(): void {
    this.dialogRef.close();
    clearInterval(this.interval);
  }

  isMessagesEmpty() {
    return (!Object.keys(this.messages).length);
  }

  isMine(message: Message) {
    return message.id_sender == this.data.getUser().id_user;
  }

  getSenderName(message: Message): string {
    if (this.isMine(message))
      return localStorage.getItem("firstname") + ' ' + localStorage.getItem("lastname")
    else
      return this.person.firstname + ' ' + this.person.lastname;
  }

  // scrollToBottom(): void {
  //   try {
  //     console.log(this.myScrollContainer);
      
  //     this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
  //   } catch (err) { }
  // }
}
