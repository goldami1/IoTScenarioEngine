import PropTypes from "prop-types";
import React, { PureComponent } from "react";

import { AkNavigationItemGroup, AkNavigationItem } from "@atlaskit/navigation";

import BitbucketBranchesIcon from "@atlaskit/icon/glyph/bitbucket/branches";
import PageIcon from "@atlaskit/icon/glyph/page";
import CalendarIcon from "@atlaskit/icon/glyph/calendar";
import EmojiObjectsIcon from "@atlaskit/icon/glyph/emoji/objects";
import EmojiNatureIcon from "@atlaskit/icon/glyph/emoji/nature";
import EmojiTravelIcon from "@atlaskit/icon/glyph/emoji/travel";

const createItems = [
  {
    title: null,
    items: [["/#event", "Create Event", "Create Event", CalendarIcon]]
  }
];

export default class CreateDrawer extends PureComponent {
  static PropTypes = {
    onItemClicked: PropTypes.func
  };

  render() {
    return (
      <div>
        {createItems.map(itemGroup => {
          return (
            <AkNavigationItemGroup
              key={itemGroup.title}
              title={itemGroup.title}
            >
              {itemGroup.items.map(item => {
                const [url, text, label, Icon] = item;
                return (
                  <AkNavigationItem
                    key={url}
                    href={url}
                    icon={<Icon label={label} />}
                    text={text.valueOf()}
                    onClick={this.props.onItemClicked}
                  />
                );
              })}
            </AkNavigationItemGroup>
          );
        })}
      </div>
    );
  }
}
