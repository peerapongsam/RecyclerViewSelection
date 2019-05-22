<?php


$json = file_get_contents("https://peerapongsam.github.io/heropedia/json/english/heroes.json");
$heroes = json_decode($json);

foreach($heroes as $hero) {
    echo 'Image("' . $hero->portrait->full . '"),' . PHP_EOL;
}
